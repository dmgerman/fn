begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2005 Andreas Rudert    Copyright (C) 2015 JabRef contributors      This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|ImportFormatReader
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|OutputPrinter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|EntryTypes
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|IdGenerator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Imports a New Economics Papers-Message from the REPEC-NEP Service.  *  *<p><a href="http://www.repec.org">RePEc (Research Papers in Economics)</a>  * is a collaborative effort of over 100 volunteers in 49 countries  * to enhance the dissemination of research in economics. The heart of  * the project is a decentralized database of working papers, journal  * articles and software components. All RePEc material is freely available.</p>  * At the time of writing RePEc holds over 300.000 items.</p>  *  *<p><a href="http://nep.repec.org">NEP (New Economic Papers)</a> is an announcement  * service which filters information on new additions to RePEc into edited  * reports. The goal is to provide subscribers with up-to-date information  * to the research literature.</p>  *  *<p>This importer is capable of importing NEP messages into JabRef.</p>  *  *<p>There is no officially defined message format for NEP. NEP messages are assumed to have  * (and almost always have) the form given by the following semi-formal grammar:  *<pre>  * NEPMessage:  *       MessageSection NEPMessage  *       MessageSection  *  * MessageSection:  *       OverviewMessageSection  *       OtherMessageSection  *  * # we skip the overview  * OverviewMessageSection:  *       'In this issue we have: ' SectionSeparator OtherStuff  *  * OtherMessageSection:  *       SectionSeparator  OtherMessageSectionContent  *  * # we skip other stuff and read only full working paper references  * OtherMessageSectionContent:  *       WorkingPaper EmptyLine OtherMessageSectionContent  *       OtherStuff EmptyLine OtherMessageSectionContent  *       ''  *  * OtherStuff:  *       NonEmptyLine OtherStuff  *       NonEmptyLine  *  * NonEmptyLine:  *       a non-empty String that does not start with a number followed by a '.'  *  * # working papers are recognized by a number followed by a '.'  * # in a non-overview section  * WorkingPaper:  *       Number'.' WhiteSpace TitleString EmptyLine Authors EmptyLine Abstract AdditionalFields  *       Number'.' WhiteSpace TitleString AdditionalFields Abstract AdditionalFields  *  * TitleString:  *       a String that may span several lines and should be joined  *  * # there must be at least one author  * Authors:  *       Author '\n' Authors  *       Author '\n'  *  * # optionally, an institution is given for an author  * Author:  *       AuthorName  *       AuthorName '(' Institution ')'  *  * # there are no rules about the name, it may be firstname lastname or lastname, firstname or anything else  * AuthorName:  *       a non-empty String without '(' or ')' characters, not spanning more that one line  *  * Institution:  *       a non-empty String that may span several lines  *  * Abstract:  *       a (possibly empty) String that may span several lines  *  * AdditionalFields:  *       AdditionalField '\n' AdditionalFields  *       EmptyLine AdditionalFields  *       ''  *  * AdditionalField:  *       'Keywords:' KeywordList  *       'URL:' non-empty String  *       'Date:' DateString  *       'JEL:' JelClassificationList  *       'By': Authors  *  * KeywordList:  *        Keyword ',' KeywordList  *        Keyword ';' KeywordList  *        Keyword  *  * Keyword:  *        non-empty String that does not contain ',' (may contain whitespace)  *  * # if no date is given, the current year as given by the system clock is assumed  * DateString:  *        'yyyy-MM-dd'  *        'yyyy-MM'  *        'yyyy'  *  * JelClassificationList:  *        JelClassification JelClassificationList  *        JelClassification  *  * # the JEL Classifications are set into a new BIBTEX-field 'jel'  * # they will appear if you add it as a field to one of the BIBTex Entry sections  * JelClassification:  *        one of the allowed classes, see http://ideas.repec.org/j/  *  * SectionSeparator:  *       '\n-----------------------------'  *</pre>  *</p>  *  * @see<a href="http://nep.repec.org">NEP</a>  * @author andreas_sf at rudert-home dot de  */
end_comment

begin_class
DECL|class|RepecNepImporter
specifier|public
class|class
name|RepecNepImporter
extends|extends
name|ImportFormat
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|RepecNepImporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|recognizedFields
specifier|private
specifier|static
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|recognizedFields
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Keywords"
argument_list|,
literal|"JEL"
argument_list|,
literal|"Date"
argument_list|,
literal|"URL"
argument_list|,
literal|"By"
argument_list|)
decl_stmt|;
DECL|field|line
specifier|private
name|int
name|line
decl_stmt|;
DECL|field|lastLine
specifier|private
name|String
name|lastLine
init|=
literal|""
decl_stmt|;
DECL|field|preLine
specifier|private
name|String
name|preLine
init|=
literal|""
decl_stmt|;
DECL|field|in
specifier|private
name|BufferedReader
name|in
decl_stmt|;
DECL|field|inOverviewSection
specifier|private
name|boolean
name|inOverviewSection
decl_stmt|;
comment|/**      * Return the name of this import format.      */
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"REPEC New Economic Papers (NEP)"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getCLIId()      */
annotation|@
name|Override
DECL|method|getCLIId ()
specifier|public
name|String
name|getCLIId
parameter_list|()
block|{
return|return
literal|"repecnep"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getExtensions()      */
annotation|@
name|Override
DECL|method|getExtensions ()
specifier|public
name|String
name|getExtensions
parameter_list|()
block|{
return|return
literal|".txt"
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#getDescription()      */
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"Imports a New Economics Papers-Message (see http://nep.repec.org)\n"
operator|+
literal|"from the REPEC-NEP Service (see http://www.repec.org).\n"
operator|+
literal|"To import papers either save a NEP message as a text file and then import or\n"
operator|+
literal|"copy&paste the papers you want to import and make sure, one of the first lines\n"
operator|+
literal|"contains the line \"nep.repec.org\"."
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#isRecognizedFormat(java.io.InputStream)      */
annotation|@
name|Override
DECL|method|isRecognizedFormat (InputStream stream)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|InputStream
name|stream
parameter_list|)
throws|throws
name|IOException
block|{
comment|// read the first couple of lines
comment|// NEP message usually contain the String 'NEP: New Economics Papers'
comment|// or, they are from nep.repec.org
name|BufferedReader
name|inBR
init|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|startOfMessage
init|=
literal|""
decl_stmt|;
name|String
name|tmpLine
init|=
name|inBR
operator|.
name|readLine
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
operator|(
name|i
operator|<
literal|25
operator|)
operator|&&
operator|(
name|tmpLine
operator|!=
literal|null
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|startOfMessage
operator|+=
name|tmpLine
expr_stmt|;
name|tmpLine
operator|=
name|inBR
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
return|return
name|startOfMessage
operator|.
name|contains
argument_list|(
literal|"NEP: New Economics Papers"
argument_list|)
operator|||
name|startOfMessage
operator|.
name|contains
argument_list|(
literal|"nep.repec.org"
argument_list|)
return|;
block|}
DECL|method|startsWithKeyword (Collection<String> keywords)
specifier|private
name|boolean
name|startsWithKeyword
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|keywords
parameter_list|)
block|{
name|boolean
name|result
init|=
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
operator|>
literal|0
decl_stmt|;
if|if
condition|(
name|result
condition|)
block|{
name|String
name|possibleKeyword
init|=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
argument_list|)
decl_stmt|;
name|result
operator|=
name|keywords
operator|.
name|contains
argument_list|(
name|possibleKeyword
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|readLine ()
specifier|private
name|void
name|readLine
parameter_list|()
throws|throws
name|IOException
block|{
name|this
operator|.
name|line
operator|++
expr_stmt|;
name|this
operator|.
name|preLine
operator|=
name|this
operator|.
name|lastLine
expr_stmt|;
name|this
operator|.
name|lastLine
operator|=
name|this
operator|.
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
comment|/**      * Read multiple lines.      *      *<p>Reads multiple lines until either      *<ul>      *<li>an empty line</li>      *<li>the end of file</li>      *<li>the next working paper or</li>      *<li>a keyword</li>      *</ul>      * is found. Whitespace at start or end of lines is trimmed except for one blank character.</p>      *      * @return  result      */
DECL|method|readMultipleLines ()
specifier|private
name|String
name|readMultipleLines
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|result
init|=
name|this
operator|.
name|lastLine
operator|.
name|trim
argument_list|()
decl_stmt|;
name|readLine
argument_list|()
expr_stmt|;
while|while
condition|(
operator|(
name|this
operator|.
name|lastLine
operator|!=
literal|null
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|lastLine
operator|.
name|trim
argument_list|()
argument_list|)
operator|&&
operator|!
name|startsWithKeyword
argument_list|(
name|RepecNepImporter
operator|.
name|recognizedFields
argument_list|)
operator|&&
operator|!
name|isStartOfWorkingPaper
argument_list|()
condition|)
block|{
name|result
operator|+=
name|this
operator|.
name|lastLine
operator|.
name|isEmpty
argument_list|()
condition|?
name|this
operator|.
name|lastLine
operator|.
name|trim
argument_list|()
else|:
literal|" "
operator|+
name|this
operator|.
name|lastLine
operator|.
name|trim
argument_list|()
expr_stmt|;
name|readLine
argument_list|()
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/**      * Implements grammar rule "TitleString".      *      * @param be      * @throws IOException      */
DECL|method|parseTitleString (BibEntry be)
specifier|private
name|void
name|parseTitleString
parameter_list|(
name|BibEntry
name|be
parameter_list|)
throws|throws
name|IOException
block|{
comment|// skip article number
name|this
operator|.
name|lastLine
operator|=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|readMultipleLines
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Implements grammer rule "Authors"      *      * @param be      * @throws IOException      */
DECL|method|parseAuthors (BibEntry be)
specifier|private
name|void
name|parseAuthors
parameter_list|(
name|BibEntry
name|be
parameter_list|)
throws|throws
name|IOException
block|{
comment|// read authors and institutions
name|String
name|authors
init|=
literal|""
decl_stmt|;
name|String
name|institutions
init|=
literal|""
decl_stmt|;
while|while
condition|(
operator|(
name|this
operator|.
name|lastLine
operator|!=
literal|null
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|lastLine
argument_list|)
operator|&&
operator|!
name|startsWithKeyword
argument_list|(
name|RepecNepImporter
operator|.
name|recognizedFields
argument_list|)
condition|)
block|{
comment|// read single author
name|String
name|author
decl_stmt|;
name|String
name|institution
init|=
literal|null
decl_stmt|;
name|boolean
name|institutionDone
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|author
operator|=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|institutionDone
operator|=
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
operator|>
literal|0
expr_stmt|;
name|institution
operator|=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
operator|+
literal|1
argument_list|,
name|institutionDone
operator|&&
operator|(
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
operator|>
operator|(
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|'('
argument_list|)
operator|+
literal|1
operator|)
operator|)
condition|?
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
else|:
name|this
operator|.
name|lastLine
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|author
operator|=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|institutionDone
operator|=
literal|true
expr_stmt|;
block|}
name|readLine
argument_list|()
expr_stmt|;
while|while
condition|(
operator|!
name|institutionDone
operator|&&
operator|(
name|this
operator|.
name|lastLine
operator|!=
literal|null
operator|)
condition|)
block|{
name|institutionDone
operator|=
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
operator|>
literal|0
expr_stmt|;
name|institution
operator|+=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|institutionDone
condition|?
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|')'
argument_list|)
else|:
name|this
operator|.
name|lastLine
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|readLine
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|author
operator|!=
literal|null
condition|)
block|{
name|authors
operator|+=
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|authors
argument_list|)
condition|?
literal|" and "
operator|+
name|author
else|:
name|author
expr_stmt|;
block|}
if|if
condition|(
name|institution
operator|!=
literal|null
condition|)
block|{
name|institutions
operator|+=
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|institutions
argument_list|)
condition|?
literal|" and "
operator|+
name|institution
else|:
name|institution
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|authors
argument_list|)
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|authors
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|institutions
argument_list|)
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"institution"
argument_list|,
name|institutions
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Implements grammar rule "Abstract".      *      * @param be      * @throws IOException      */
DECL|method|parseAbstract (BibEntry be)
specifier|private
name|void
name|parseAbstract
parameter_list|(
name|BibEntry
name|be
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|theabstract
init|=
name|readMultipleLines
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|theabstract
argument_list|)
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|theabstract
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Implements grammar rule "AdditionalFields".      *      * @param be      * @throws IOException      */
DECL|method|parseAdditionalFields (BibEntry be, boolean multilineUrlFieldAllowed)
specifier|private
name|void
name|parseAdditionalFields
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|boolean
name|multilineUrlFieldAllowed
parameter_list|)
throws|throws
name|IOException
block|{
comment|// one empty line is possible before fields start
if|if
condition|(
operator|(
name|this
operator|.
name|lastLine
operator|!=
literal|null
operator|)
operator|&&
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|lastLine
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
block|{
name|readLine
argument_list|()
expr_stmt|;
block|}
comment|// read other fields
while|while
condition|(
operator|(
name|this
operator|.
name|lastLine
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|isStartOfWorkingPaper
argument_list|()
operator|&&
operator|(
name|startsWithKeyword
argument_list|(
name|RepecNepImporter
operator|.
name|recognizedFields
argument_list|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|lastLine
argument_list|)
operator|)
condition|)
block|{
comment|// if multiple lines for a field are allowed and field consists of multiple lines, join them
name|String
name|keyword
init|=
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|lastLine
argument_list|)
condition|?
literal|""
else|:
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|// skip keyword
name|this
operator|.
name|lastLine
operator|=
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|lastLine
argument_list|)
condition|?
literal|""
else|:
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
operator|+
literal|1
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
comment|// parse keywords field
if|if
condition|(
literal|"Keywords"
operator|.
name|equals
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
name|String
name|content
init|=
name|readMultipleLines
argument_list|()
decl_stmt|;
name|String
index|[]
name|keywords
init|=
name|content
operator|.
name|split
argument_list|(
literal|"[,;]"
argument_list|)
decl_stmt|;
name|String
name|keywordStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|keyword1
range|:
name|keywords
control|)
block|{
name|keywordStr
operator|+=
literal|" '"
operator|+
name|keyword1
operator|.
name|trim
argument_list|()
operator|+
literal|"'"
expr_stmt|;
block|}
name|be
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
name|keywordStr
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// parse JEL field
block|}
elseif|else
if|if
condition|(
literal|"JEL"
operator|.
name|equals
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"jel"
argument_list|,
name|readMultipleLines
argument_list|()
argument_list|)
expr_stmt|;
comment|// parse date field
block|}
elseif|else
if|if
condition|(
name|keyword
operator|.
name|startsWith
argument_list|(
literal|"Date"
argument_list|)
condition|)
block|{
name|Date
name|date
init|=
literal|null
decl_stmt|;
name|String
name|content
init|=
name|readMultipleLines
argument_list|()
decl_stmt|;
name|String
index|[]
name|recognizedDateFormats
init|=
operator|new
name|String
index|[]
block|{
literal|"yyyy-MM-dd"
block|,
literal|"yyyy-MM"
block|,
literal|"yyyy"
block|}
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
init|;
operator|(
name|i
operator|<
name|recognizedDateFormats
operator|.
name|length
operator|)
operator|&&
operator|(
name|date
operator|==
literal|null
operator|)
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|date
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
name|recognizedDateFormats
index|[
name|i
index|]
argument_list|)
operator|.
name|parse
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
comment|// wrong format
block|}
block|}
name|Calendar
name|cal
init|=
operator|new
name|GregorianCalendar
argument_list|()
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|date
operator|!=
literal|null
condition|?
name|date
else|:
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|date
operator|!=
literal|null
operator|)
operator|&&
name|recognizedDateFormats
index|[
name|i
operator|-
literal|1
index|]
operator|.
name|contains
argument_list|(
literal|"MM"
argument_list|)
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MONTH
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// parse URL field
block|}
elseif|else
if|if
condition|(
name|keyword
operator|.
name|startsWith
argument_list|(
literal|"URL"
argument_list|)
condition|)
block|{
name|String
name|content
decl_stmt|;
if|if
condition|(
name|multilineUrlFieldAllowed
condition|)
block|{
name|content
operator|=
name|readMultipleLines
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|content
operator|=
name|this
operator|.
name|lastLine
expr_stmt|;
name|readLine
argument_list|()
expr_stmt|;
block|}
name|be
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
name|content
argument_list|)
expr_stmt|;
comment|// authors field
block|}
elseif|else
if|if
condition|(
name|keyword
operator|.
name|startsWith
argument_list|(
literal|"By"
argument_list|)
condition|)
block|{
comment|// parse authors
name|parseAuthors
argument_list|(
name|be
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|readLine
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * if line starts with a string of the form 'x. ' and we are not in the overview      * section, we have a working paper entry we are interested in      */
DECL|method|isStartOfWorkingPaper ()
specifier|private
name|boolean
name|isStartOfWorkingPaper
parameter_list|()
block|{
return|return
name|this
operator|.
name|lastLine
operator|.
name|matches
argument_list|(
literal|"\\d+\\.\\s.*"
argument_list|)
operator|&&
operator|!
name|this
operator|.
name|inOverviewSection
operator|&&
literal|""
operator|.
name|equals
argument_list|(
name|this
operator|.
name|preLine
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see net.sf.jabref.imports.ImportFormat#importEntries(java.io.InputStream)      */
annotation|@
name|Override
DECL|method|importEntries (InputStream stream, OutputPrinter status)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|importEntries
parameter_list|(
name|InputStream
name|stream
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
throws|throws
name|IOException
block|{
name|ArrayList
argument_list|<
name|BibEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|String
name|paperNoStr
init|=
literal|null
decl_stmt|;
name|this
operator|.
name|line
operator|=
literal|0
expr_stmt|;
try|try
block|{
name|this
operator|.
name|in
operator|=
operator|new
name|BufferedReader
argument_list|(
name|ImportFormatReader
operator|.
name|getReaderDefaultEncoding
argument_list|(
name|stream
argument_list|)
argument_list|)
expr_stmt|;
name|readLine
argument_list|()
expr_stmt|;
comment|// skip header and editor information
while|while
condition|(
name|this
operator|.
name|lastLine
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|this
operator|.
name|lastLine
operator|.
name|startsWith
argument_list|(
literal|"-----------------------------"
argument_list|)
condition|)
block|{
name|this
operator|.
name|inOverviewSection
operator|=
name|this
operator|.
name|preLine
operator|.
name|startsWith
argument_list|(
literal|"In this issue we have"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isStartOfWorkingPaper
argument_list|()
condition|)
block|{
name|BibEntry
name|be
init|=
operator|new
name|BibEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|be
operator|.
name|setType
argument_list|(
name|EntryTypes
operator|.
name|getType
argument_list|(
literal|"techreport"
argument_list|)
argument_list|)
expr_stmt|;
name|paperNoStr
operator|=
name|this
operator|.
name|lastLine
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|this
operator|.
name|lastLine
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
name|parseTitleString
argument_list|(
name|be
argument_list|)
expr_stmt|;
if|if
condition|(
name|startsWithKeyword
argument_list|(
name|RepecNepImporter
operator|.
name|recognizedFields
argument_list|)
condition|)
block|{
name|parseAdditionalFields
argument_list|(
name|be
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|readLine
argument_list|()
expr_stmt|;
comment|// skip empty line
name|parseAuthors
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|readLine
argument_list|()
expr_stmt|;
comment|// skip empty line
block|}
if|if
condition|(
operator|!
name|startsWithKeyword
argument_list|(
name|RepecNepImporter
operator|.
name|recognizedFields
argument_list|)
condition|)
block|{
name|parseAbstract
argument_list|(
name|be
argument_list|)
expr_stmt|;
block|}
name|parseAdditionalFields
argument_list|(
name|be
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|paperNoStr
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|preLine
operator|=
name|this
operator|.
name|lastLine
expr_stmt|;
name|readLine
argument_list|()
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|String
name|message
init|=
literal|"Error in REPEC-NEP import on line "
operator|+
name|this
operator|.
name|line
decl_stmt|;
if|if
condition|(
name|paperNoStr
operator|!=
literal|null
condition|)
block|{
name|message
operator|+=
literal|", paper no. "
operator|+
name|paperNoStr
operator|+
literal|": "
expr_stmt|;
block|}
name|message
operator|+=
name|e
operator|.
name|getMessage
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
operator|(
name|e
operator|instanceof
name|IOException
operator|)
condition|)
block|{
name|e
operator|=
operator|new
name|IOException
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
throw|throw
operator|(
name|IOException
operator|)
name|e
throw|;
block|}
return|return
name|bibitems
return|;
block|}
block|}
end_class

end_unit

