begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ImportFormatPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|Importer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|StandardFileType
import|;
end_import

begin_import
import|import
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|UnknownField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
operator|.
name|StandardEntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Imports a New Economics Papers-Message from the REPEC-NEP Service.  *<p>  *<p><a href="http://www.repec.org">RePEc (Research Papers in Economics)</a>  * is a collaborative effort of over 100 volunteers in 49 countries  * to enhance the dissemination of research in economics. The heart of  * the project is a decentralized database of working papers, journal  * articles and software components. All RePEc material is freely available.</p>  * At the time of writing RePEc holds over 300.000 items.</p>  *<p>  *<p><a href="http://nep.repec.org">NEP (New Economic Papers)</a> is an announcement  * service which filters information on new additions to RePEc into edited  * reports. The goal is to provide subscribers with up-to-date information  * to the research literature.</p>  *<p>  *<p>This importer is capable of importing NEP messages into JabRef.</p>  *<p>  *<p>There is no officially defined message format for NEP. NEP messages are assumed to have  * (and almost always have) the form given by the following semi-formal grammar:  *<pre>  * NEPMessage:  *       MessageSection NEPMessage  *       MessageSection  *  * MessageSection:  *       OverviewMessageSection  *       OtherMessageSection  *  * # we skip the overview  * OverviewMessageSection:  *       'In this issue we have: ' SectionSeparator OtherStuff  *  * OtherMessageSection:  *       SectionSeparator  OtherMessageSectionContent  *  * # we skip other stuff and read only full working paper references  * OtherMessageSectionContent:  *       WorkingPaper EmptyLine OtherMessageSectionContent  *       OtherStuff EmptyLine OtherMessageSectionContent  *       ''  *  * OtherStuff:  *       NonEmptyLine OtherStuff  *       NonEmptyLine  *  * NonEmptyLine:  *       a non-empty String that does not start with a number followed by a '.'  *  * # working papers are recognized by a number followed by a '.'  * # in a non-overview section  * WorkingPaper:  *       Number'.' WhiteSpace TitleString EmptyLine Authors EmptyLine Abstract AdditionalFields  *       Number'.' WhiteSpace TitleString AdditionalFields Abstract AdditionalFields  *  * TitleString:  *       a String that may span several lines and should be joined  *  * # there must be at least one author  * Authors:  *       Author '\n' Authors  *       Author '\n'  *  * # optionally, an institution is given for an author  * Author:  *       AuthorName  *       AuthorName '(' Institution ')'  *  * # there are no rules about the name, it may be firstname lastname or lastname, firstname or anything else  * AuthorName:  *       a non-empty String without '(' or ')' characters, not spanning more that one line  *  * Institution:  *       a non-empty String that may span several lines  *  * Abstract:  *       a (possibly empty) String that may span several lines  *  * AdditionalFields:  *       AdditionalField '\n' AdditionalFields  *       EmptyLine AdditionalFields  *       ''  *  * AdditionalField:  *       'Keywords:' KeywordList  *       'URL:' non-empty String  *       'Date:' DateString  *       'JEL:' JelClassificationList  *       'By': Authors  *  * KeywordList:  *        Keyword ',' KeywordList  *        Keyword ';' KeywordList  *        Keyword  *  * Keyword:  *        non-empty String that does not contain ',' (may contain whitespace)  *  * # if no date is given, the current year as given by the system clock is assumed  * DateString:  *        'yyyy-MM-dd'  *        'yyyy-MM'  *        'yyyy'  *  * JelClassificationList:  *        JelClassification JelClassificationList  *        JelClassification  *  * # the JEL Classifications are set into a new BIBTEX-field 'jel'  * # they will appear if you add it as a field to one of the BIBTex Entry sections  * JelClassification:  *        one of the allowed classes, see http://ideas.repec.org/j/  *  * SectionSeparator:  *       '\n-----------------------------'  *</pre>  *</p>  */
end_comment

begin_class
DECL|class|RepecNepImporter
specifier|public
class|class
name|RepecNepImporter
extends|extends
name|Importer
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|RepecNepImporter
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|RECOGNIZED_FIELDS
specifier|private
specifier|static
specifier|final
name|Collection
argument_list|<
name|String
argument_list|>
name|RECOGNIZED_FIELDS
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
DECL|field|importFormatPreferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
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
DECL|field|inOverviewSection
specifier|private
name|boolean
name|inOverviewSection
decl_stmt|;
DECL|method|RepecNepImporter (ImportFormatPreferences importFormatPreferences)
specifier|public
name|RepecNepImporter
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|this
operator|.
name|importFormatPreferences
operator|=
name|importFormatPreferences
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"REPEC New Economic Papers (NEP)"
return|;
block|}
annotation|@
name|Override
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
literal|"repecnep"
return|;
block|}
annotation|@
name|Override
DECL|method|getFileType ()
specifier|public
name|StandardFileType
name|getFileType
parameter_list|()
block|{
return|return
name|StandardFileType
operator|.
name|TXT
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"Imports a New Economics Papers-Message from the REPEC-NEP Service."
return|;
block|}
annotation|@
name|Override
DECL|method|isRecognizedFormat (BufferedReader reader)
specifier|public
name|boolean
name|isRecognizedFormat
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
comment|// read the first couple of lines
comment|// NEP message usually contain the String 'NEP: New Economics Papers'
comment|// or, they are from nep.repec.org
name|StringBuilder
name|startOfMessage
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|tmpLine
init|=
name|reader
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
operator|.
name|append
argument_list|(
name|tmpLine
argument_list|)
expr_stmt|;
name|tmpLine
operator|=
name|reader
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
return|return
name|startOfMessage
operator|.
name|toString
argument_list|()
operator|.
name|contains
argument_list|(
literal|"NEP: New Economics Papers"
argument_list|)
operator|||
name|startOfMessage
operator|.
name|toString
argument_list|()
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
operator|>=
literal|1
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
DECL|method|readLine (BufferedReader in)
specifier|private
name|void
name|readLine
parameter_list|(
name|BufferedReader
name|in
parameter_list|)
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
name|in
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
comment|/**      * Read multiple lines.      *<p>      *<p>Reads multiple lines until either      *<ul>      *<li>an empty line</li>      *<li>the end of file</li>      *<li>the next working paper or</li>      *<li>a keyword</li>      *</ul>      * is found. Whitespace at start or end of lines is trimmed except for one blank character.</p>      *      * @return result      */
DECL|method|readMultipleLines (BufferedReader in)
specifier|private
name|String
name|readMultipleLines
parameter_list|(
name|BufferedReader
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|(
name|this
operator|.
name|lastLine
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
name|readLine
argument_list|(
name|in
argument_list|)
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
name|RECOGNIZED_FIELDS
argument_list|)
operator|&&
operator|!
name|isStartOfWorkingPaper
argument_list|()
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
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
argument_list|)
expr_stmt|;
name|readLine
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Implements grammar rule "TitleString".      *      * @param be      * @throws IOException      */
DECL|method|parseTitleString (BibEntry be, BufferedReader in)
specifier|private
name|void
name|parseTitleString
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|BufferedReader
name|in
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
argument_list|)
expr_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
name|readMultipleLines
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Implements grammar rule "Authors"      *      * @param be      * @throws IOException      */
DECL|method|parseAuthors (BibEntry be, BufferedReader in)
specifier|private
name|void
name|parseAuthors
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|BufferedReader
name|in
parameter_list|)
throws|throws
name|IOException
block|{
comment|// read authors and institutions
name|List
argument_list|<
name|String
argument_list|>
name|authors
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|institutions
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|RECOGNIZED_FIELDS
argument_list|)
condition|)
block|{
comment|// read single author
name|String
name|author
decl_stmt|;
name|StringBuilder
name|institution
init|=
operator|new
name|StringBuilder
argument_list|()
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
operator|>=
literal|1
expr_stmt|;
name|institution
operator|.
name|append
argument_list|(
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
argument_list|)
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
name|trim
argument_list|()
expr_stmt|;
name|institutionDone
operator|=
literal|true
expr_stmt|;
block|}
name|readLine
argument_list|(
name|in
argument_list|)
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
operator|>=
literal|1
expr_stmt|;
name|institution
operator|.
name|append
argument_list|(
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
argument_list|)
expr_stmt|;
name|readLine
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
name|authors
operator|.
name|add
argument_list|(
name|author
argument_list|)
expr_stmt|;
if|if
condition|(
name|institution
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|institutions
operator|.
name|append
argument_list|(
operator|(
name|institutions
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|?
name|institution
operator|.
name|toString
argument_list|()
else|:
literal|" and "
operator|+
name|institution
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|authors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|authors
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|institutions
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|be
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|INSTITUTION
argument_list|,
name|institutions
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Implements grammar rule "Abstract".      *      * @param be      * @throws IOException      */
DECL|method|parseAbstract (BibEntry be, BufferedReader in)
specifier|private
name|void
name|parseAbstract
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|BufferedReader
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|theabstract
init|=
name|readMultipleLines
argument_list|(
name|in
argument_list|)
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
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
name|theabstract
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Implements grammar rule "AdditionalFields".      *      * @param be      * @throws IOException      */
DECL|method|parseAdditionalFields (BibEntry be, boolean multilineUrlFieldAllowed, BufferedReader in)
specifier|private
name|void
name|parseAdditionalFields
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|boolean
name|multilineUrlFieldAllowed
parameter_list|,
name|BufferedReader
name|in
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
argument_list|(
name|in
argument_list|)
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
name|RECOGNIZED_FIELDS
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
argument_list|(
name|in
argument_list|)
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
name|be
operator|.
name|addKeywords
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|keywords
argument_list|)
argument_list|,
name|importFormatPreferences
operator|.
name|getKeywordSeparator
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
operator|new
name|UnknownField
argument_list|(
literal|"jel"
argument_list|)
argument_list|,
name|readMultipleLines
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
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
comment|// parse date field
name|String
name|content
init|=
name|readMultipleLines
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|Date
operator|.
name|parse
argument_list|(
name|content
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|be
operator|::
name|setDate
argument_list|)
expr_stmt|;
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
argument_list|(
name|in
argument_list|)
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
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
name|be
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
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
argument_list|,
name|in
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|readLine
argument_list|(
name|in
argument_list|)
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
annotation|@
name|Override
DECL|method|importDatabase (BufferedReader reader)
specifier|public
name|ParserResult
name|importDatabase
parameter_list|(
name|BufferedReader
name|reader
parameter_list|)
throws|throws
name|IOException
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|List
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
name|readLine
argument_list|(
name|reader
argument_list|)
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
name|StandardEntryType
operator|.
name|TechReport
argument_list|)
decl_stmt|;
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
argument_list|,
name|reader
argument_list|)
expr_stmt|;
if|if
condition|(
name|startsWithKeyword
argument_list|(
name|RepecNepImporter
operator|.
name|RECOGNIZED_FIELDS
argument_list|)
condition|)
block|{
name|parseAdditionalFields
argument_list|(
name|be
argument_list|,
literal|false
argument_list|,
name|reader
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|readLine
argument_list|(
name|reader
argument_list|)
expr_stmt|;
comment|// skip empty line
name|parseAuthors
argument_list|(
name|be
argument_list|,
name|reader
argument_list|)
expr_stmt|;
name|readLine
argument_list|(
name|reader
argument_list|)
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
name|RECOGNIZED_FIELDS
argument_list|)
condition|)
block|{
name|parseAbstract
argument_list|(
name|be
argument_list|,
name|reader
argument_list|)
expr_stmt|;
block|}
name|parseAdditionalFields
argument_list|(
name|be
argument_list|,
literal|true
argument_list|,
name|reader
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
argument_list|(
name|reader
argument_list|)
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
name|getLocalizedMessage
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
return|return
name|ParserResult
operator|.
name|fromErrorMessage
argument_list|(
name|message
argument_list|)
return|;
block|}
return|return
operator|new
name|ParserResult
argument_list|(
name|bibitems
argument_list|)
return|;
block|}
block|}
end_class

end_unit

