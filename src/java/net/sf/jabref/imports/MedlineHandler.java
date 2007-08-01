begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|BibtexEntry
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
name|Globals
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
name|Util
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|Attributes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|helpers
operator|.
name|DefaultHandler
import|;
end_import

begin_comment
comment|/*   Copyright (C) 2002-2003 Morten O. Alver& Nizar N. Batada   All programs in this directory and   subdirectories are published under the GNU General Public License as   described below.    This program is free software; you can redistribute it and/or modify   it under the terms of the GNU General Public License as published by   the Free Software Foundation; either version 2 of the License, or (at   your option) any later version.    This program is distributed in the hope that it will be useful, but   WITHOUT ANY WARRANTY; without even the implied warranty of   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU   General Public License for more details.    You should have received a copy of the GNU General Public License   along with this program; if not, write to the Free Software   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307   USA    Further information about the GNU GPL is available at:   http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_class
DECL|class|MedlineHandler
specifier|public
class|class
name|MedlineHandler
extends|extends
name|DefaultHandler
block|{
DECL|field|bibitems
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|bibitems
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|inTitle
DECL|field|inYear
name|boolean
name|inTitle
init|=
literal|false
decl_stmt|,
name|inYear
init|=
literal|false
decl_stmt|,
DECL|field|inJournal
DECL|field|inMonth
name|inJournal
init|=
literal|false
decl_stmt|,
name|inMonth
init|=
literal|false
decl_stmt|,
DECL|field|inVolume
DECL|field|inAuthorList
name|inVolume
init|=
literal|false
decl_stmt|,
name|inAuthorList
init|=
literal|false
decl_stmt|,
DECL|field|inAuthor
DECL|field|inLastName
name|inAuthor
init|=
literal|false
decl_stmt|,
name|inLastName
init|=
literal|false
decl_stmt|,
DECL|field|inInitials
DECL|field|inMedlinePgn
name|inInitials
init|=
literal|false
decl_stmt|,
name|inMedlinePgn
init|=
literal|false
decl_stmt|,
DECL|field|inMedlineID
DECL|field|inURL
name|inMedlineID
init|=
literal|false
decl_stmt|,
name|inURL
init|=
literal|false
decl_stmt|,
DECL|field|inIssue
DECL|field|inPubDate
name|inIssue
init|=
literal|false
decl_stmt|,
name|inPubDate
init|=
literal|false
decl_stmt|,
DECL|field|inUrl
DECL|field|inForename
DECL|field|inAbstractText
DECL|field|inMedlineDate
name|inUrl
init|=
literal|false
decl_stmt|,
name|inForename
init|=
literal|false
decl_stmt|,
name|inAbstractText
init|=
literal|false
decl_stmt|,
name|inMedlineDate
init|=
literal|false
decl_stmt|,
DECL|field|inPubMedID
DECL|field|inDescriptorName
DECL|field|inDoi
DECL|field|inPii
name|inPubMedID
init|=
literal|false
decl_stmt|,
name|inDescriptorName
init|=
literal|false
decl_stmt|,
name|inDoi
init|=
literal|false
decl_stmt|,
name|inPii
init|=
literal|false
decl_stmt|,
DECL|field|inAffiliation
name|inAffiliation
init|=
literal|false
decl_stmt|;
DECL|field|title
DECL|field|journal
DECL|field|keywords
DECL|field|author
name|String
name|title
init|=
literal|""
decl_stmt|,
name|journal
init|=
literal|""
decl_stmt|,
name|keywords
init|=
literal|""
decl_stmt|,
name|author
init|=
literal|""
decl_stmt|,
DECL|field|lastName
DECL|field|year
DECL|field|forename
DECL|field|abstractText
DECL|field|affiliation
name|lastName
init|=
literal|""
decl_stmt|,
name|year
init|=
literal|""
decl_stmt|,
name|forename
init|=
literal|""
decl_stmt|,
name|abstractText
init|=
literal|""
decl_stmt|,
name|affiliation
init|=
literal|""
decl_stmt|;
DECL|field|month
DECL|field|volume
DECL|field|lastname
DECL|field|initials
DECL|field|number
DECL|field|page
DECL|field|medlineID
DECL|field|url
DECL|field|MedlineDate
name|String
name|month
init|=
literal|""
decl_stmt|,
name|volume
init|=
literal|""
decl_stmt|,
name|lastname
init|=
literal|""
decl_stmt|,
name|initials
init|=
literal|""
decl_stmt|,
name|number
init|=
literal|""
decl_stmt|,
name|page
init|=
literal|""
decl_stmt|,
name|medlineID
init|=
literal|""
decl_stmt|,
name|url
init|=
literal|""
decl_stmt|,
name|MedlineDate
init|=
literal|""
decl_stmt|;
DECL|field|series
DECL|field|editor
DECL|field|booktitle
DECL|field|type
DECL|field|key
DECL|field|address
name|String
name|series
init|=
literal|""
decl_stmt|,
name|editor
init|=
literal|""
decl_stmt|,
name|booktitle
init|=
literal|""
decl_stmt|,
name|type
init|=
literal|"article"
decl_stmt|,
name|key
init|=
literal|""
decl_stmt|,
name|address
init|=
literal|""
decl_stmt|,
DECL|field|pubmedid
DECL|field|doi
DECL|field|pii
name|pubmedid
init|=
literal|""
decl_stmt|,
name|doi
init|=
literal|""
decl_stmt|,
name|pii
init|=
literal|""
decl_stmt|;
DECL|field|authors
name|ArrayList
argument_list|<
name|String
argument_list|>
name|authors
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|descriptors
name|TreeSet
argument_list|<
name|String
argument_list|>
name|descriptors
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|// To gather keywords
DECL|field|rowNum
name|int
name|rowNum
init|=
literal|0
decl_stmt|;
DECL|field|KEYWORD_SEPARATOR
specifier|private
specifier|static
specifier|final
name|String
name|KEYWORD_SEPARATOR
init|=
literal|"; "
decl_stmt|;
DECL|method|getItems ()
specifier|public
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|getItems
parameter_list|()
block|{
return|return
name|bibitems
return|;
block|}
DECL|method|MedlineHandler ()
specifier|public
name|MedlineHandler
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|startElement (String uri, String localName, String qName, Attributes atts)
specifier|public
name|void
name|startElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|qName
parameter_list|,
name|Attributes
name|atts
parameter_list|)
block|{
comment|//		public void startElement(String localName, Attributes atts) {
comment|// Get the number of attribute
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"PubmedArticle"
argument_list|)
condition|)
block|{}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"ArticleTitle"
argument_list|)
condition|)
block|{
name|inTitle
operator|=
literal|true
expr_stmt|;
name|title
operator|=
literal|""
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"PubDate"
argument_list|)
condition|)
block|{
name|inPubDate
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Year"
argument_list|)
operator|&&
name|inPubDate
operator|==
literal|true
condition|)
block|{
name|inYear
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"MedlineDate"
argument_list|)
operator|&&
name|inPubDate
operator|==
literal|true
condition|)
block|{
name|inMedlineDate
operator|=
literal|true
expr_stmt|;
block|}
comment|// medline date does not have 4 digit dates instead it has multiyear etc
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"MedlineTA"
argument_list|)
condition|)
block|{
name|inJournal
operator|=
literal|true
expr_stmt|;
name|journal
operator|=
literal|""
expr_stmt|;
block|}
comment|//journal name
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Month"
argument_list|)
operator|&&
name|inPubDate
operator|==
literal|true
condition|)
block|{
name|inMonth
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Volume"
argument_list|)
condition|)
block|{
name|inVolume
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"AuthorList"
argument_list|)
condition|)
block|{
name|inAuthorList
operator|=
literal|true
expr_stmt|;
name|authors
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"DescriptorName"
argument_list|)
condition|)
block|{
comment|//keyword="";
name|inDescriptorName
operator|=
literal|true
expr_stmt|;
comment|//descriptorName="";
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Author"
argument_list|)
condition|)
block|{
name|inAuthor
operator|=
literal|true
expr_stmt|;
name|author
operator|=
literal|""
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"CollectiveName"
argument_list|)
condition|)
block|{
name|inForename
operator|=
literal|true
expr_stmt|;
name|forename
operator|=
literal|""
expr_stmt|;
block|}
comment|// Morten A. 20040513.
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"PMID"
argument_list|)
condition|)
block|{
comment|// Set PMID only once, because there can be<CommentIn> tags later on that
comment|// contain IDs of different articles.
if|if
condition|(
name|pubmedid
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|inPubMedID
operator|=
literal|true
expr_stmt|;
name|pubmedid
operator|=
literal|""
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"LastName"
argument_list|)
condition|)
block|{
name|inLastName
operator|=
literal|true
expr_stmt|;
name|lastName
operator|=
literal|""
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"ForeName"
argument_list|)
operator|||
name|localName
operator|.
name|equals
argument_list|(
literal|"FirstName"
argument_list|)
condition|)
block|{
name|inForename
operator|=
literal|true
expr_stmt|;
name|forename
operator|=
literal|""
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Issue"
argument_list|)
condition|)
block|{
name|inIssue
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"MedlinePgn"
argument_list|)
condition|)
block|{
name|inMedlinePgn
operator|=
literal|true
expr_stmt|;
block|}
comment|//pagenumber
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"URL"
argument_list|)
condition|)
block|{
name|inUrl
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Initials"
argument_list|)
condition|)
block|{
name|inInitials
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"AbstractText"
argument_list|)
condition|)
block|{
name|inAbstractText
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"ArticleId"
argument_list|)
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|atts
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
name|atts
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|.
name|equals
argument_list|(
literal|"doi"
argument_list|)
condition|)
name|inDoi
operator|=
literal|true
expr_stmt|;
elseif|else
if|if
condition|(
name|value
operator|.
name|equals
argument_list|(
literal|"pii"
argument_list|)
condition|)
name|inPii
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Affiliation"
argument_list|)
condition|)
block|{
name|inAffiliation
operator|=
literal|true
expr_stmt|;
block|}
return|return;
block|}
DECL|method|join (Object[] sa,String delim)
name|String
name|join
parameter_list|(
name|Object
index|[]
name|sa
parameter_list|,
name|String
name|delim
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|sa
index|[
literal|0
index|]
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|sa
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|delim
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|sa
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|makeBibtexString ()
name|String
name|makeBibtexString
parameter_list|()
block|{
name|String
name|out
init|=
literal|""
decl_stmt|;
comment|// PENDING jeffrey.kuhn@yale.edu 2005-05-27 : added call to fixPageRange
name|out
operator|=
literal|"article{,\n"
operator|+
literal|" author = { "
operator|+
name|author
operator|+
literal|" },\n title = { "
operator|+
name|title
operator|+
literal|"},\n journal ={ "
operator|+
name|journal
operator|+
literal|"},\n year = "
operator|+
name|year
operator|+
literal|"},\n volume = { "
operator|+
name|volume
operator|+
literal|"},\n number = { "
operator|+
name|number
operator|+
literal|"},\n pages = { "
operator|+
name|fixPageRange
argument_list|(
name|page
argument_list|)
operator|+
literal|"},\n abstract = { "
operator|+
name|abstractText
operator|+
literal|"},\n}"
expr_stmt|;
return|return
name|out
return|;
block|}
DECL|method|endElement ( String uri, String localName, String qName )
specifier|public
name|void
name|endElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|qName
parameter_list|)
block|{
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"PubmedArticle"
argument_list|)
condition|)
block|{
comment|//bibitems.add( new Bibitem(null, makeBibtexString(), Globals.nextKey(),"-1" )	 );
comment|// check if year ="" then give medline date instead
if|if
condition|(
name|year
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|MedlineDate
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
comment|// multi-year date format
comment|//System.out.println(MedlineDate);
name|year
operator|=
name|MedlineDate
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|4
argument_list|)
expr_stmt|;
comment|//Matcher m = Pattern.compile("\\b[0-9]{4}\\b").matcher(MedlineDate);
comment|//if(m.matches())
comment|//year = m.group();
block|}
block|}
comment|// Build a string from the collected keywords:
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|iterator
init|=
name|descriptors
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|s
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|s
argument_list|)
expr_stmt|;
if|if
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
name|KEYWORD_SEPARATOR
argument_list|)
expr_stmt|;
block|}
name|keywords
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
name|BibtexEntry
name|b
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|,
comment|//Globals.DEFAULT_BIBTEXENTRY_ID,
name|Globals
operator|.
name|getEntryType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
decl_stmt|;
comment|// id assumes an existing database so don't create one here
if|if
condition|(
operator|!
name|author
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|b
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|ImportFormatReader
operator|.
name|expandAuthorInitials
argument_list|(
name|author
argument_list|)
argument_list|)
expr_stmt|;
name|author
operator|=
literal|""
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|title
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|journal
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
name|journal
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|year
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
comment|// PENDING jeffrey.kuhn@yale.edu 2005-05-27 : added call to fixPageRange
if|if
condition|(
operator|!
name|page
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
name|fixPageRange
argument_list|(
name|page
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|volume
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|volume
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|abstractText
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|abstractText
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
literal|"\\\\%"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|keywords
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
name|keywords
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|month
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|month
argument_list|)
expr_stmt|;
comment|//if (!url.equals("")) b.setField("url",url);
if|if
condition|(
operator|!
name|number
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
name|number
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|doi
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|b
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|doi
argument_list|)
expr_stmt|;
name|b
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://dx.doi.org/"
operator|+
name|doi
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|pii
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"pii"
argument_list|,
name|pii
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|affiliation
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"institution"
argument_list|,
name|affiliation
argument_list|)
expr_stmt|;
comment|// PENDING jeffrey.kuhn@yale.edu 2005-05-27 : added "pmid" bibtex field
comment|// Older references do not have doi entries, but every
comment|// medline entry has a unique pubmed ID (aka primary ID).
comment|// Add a bibtex field for the pubmed ID for future use.
if|if
condition|(
operator|!
name|pubmedid
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|b
operator|.
name|setField
argument_list|(
literal|"pmid"
argument_list|,
name|pubmedid
argument_list|)
expr_stmt|;
name|bibitems
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
name|abstractText
operator|=
literal|""
expr_stmt|;
name|author
operator|=
literal|""
expr_stmt|;
name|title
operator|=
literal|""
expr_stmt|;
name|journal
operator|=
literal|""
expr_stmt|;
name|keywords
operator|=
literal|""
expr_stmt|;
name|doi
operator|=
literal|""
expr_stmt|;
name|pii
operator|=
literal|""
expr_stmt|;
name|year
operator|=
literal|""
expr_stmt|;
name|forename
operator|=
literal|""
expr_stmt|;
name|lastName
operator|=
literal|""
expr_stmt|;
name|abstractText
operator|=
literal|""
expr_stmt|;
name|affiliation
operator|=
literal|""
expr_stmt|;
name|pubmedid
operator|=
literal|""
expr_stmt|;
name|month
operator|=
literal|""
expr_stmt|;
name|volume
operator|=
literal|""
expr_stmt|;
name|lastname
operator|=
literal|""
expr_stmt|;
name|initials
operator|=
literal|""
expr_stmt|;
name|number
operator|=
literal|""
expr_stmt|;
name|page
operator|=
literal|""
expr_stmt|;
name|medlineID
operator|=
literal|""
expr_stmt|;
name|url
operator|=
literal|""
expr_stmt|;
name|MedlineDate
operator|=
literal|""
expr_stmt|;
name|descriptors
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"ArticleTitle"
argument_list|)
condition|)
block|{
name|inTitle
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"PubDate"
argument_list|)
condition|)
block|{
name|inPubDate
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Year"
argument_list|)
condition|)
block|{
name|inYear
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"PMID"
argument_list|)
condition|)
block|{
name|inPubMedID
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"MedlineDate"
argument_list|)
condition|)
block|{
name|inMedlineDate
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"MedlineTA"
argument_list|)
condition|)
block|{
name|inJournal
operator|=
literal|false
expr_stmt|;
block|}
comment|//journal name
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Month"
argument_list|)
condition|)
block|{
name|inMonth
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Volume"
argument_list|)
condition|)
block|{
name|inVolume
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"AuthorList"
argument_list|)
condition|)
block|{
name|author
operator|=
name|join
argument_list|(
name|authors
operator|.
name|toArray
argument_list|()
argument_list|,
literal|" and "
argument_list|)
expr_stmt|;
name|inAuthorList
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Author"
argument_list|)
condition|)
block|{
comment|// forename sometimes has initials with " " in middle: is pattern [A-Z] [A-Z]
comment|// when above is the case replace it with initials
if|if
condition|(
name|forename
operator|.
name|length
argument_list|()
operator|==
literal|3
operator|&&
name|forename
operator|.
name|charAt
argument_list|(
literal|1
argument_list|)
operator|==
literal|' '
condition|)
block|{
name|forename
operator|=
name|initials
expr_stmt|;
block|}
name|author
operator|=
name|forename
operator|+
literal|" "
operator|+
name|lastname
expr_stmt|;
comment|//author = initials + " " + lastname;
name|authors
operator|.
name|add
argument_list|(
name|author
argument_list|)
expr_stmt|;
name|inAuthor
operator|=
literal|false
expr_stmt|;
name|forename
operator|=
literal|""
expr_stmt|;
name|initials
operator|=
literal|""
expr_stmt|;
name|lastname
operator|=
literal|""
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"DescriptorName"
argument_list|)
condition|)
name|inDescriptorName
operator|=
literal|false
expr_stmt|;
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"LastName"
argument_list|)
condition|)
block|{
name|inLastName
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"ForeName"
argument_list|)
operator|||
name|localName
operator|.
name|equals
argument_list|(
literal|"FirstName"
argument_list|)
condition|)
block|{
name|inForename
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Issue"
argument_list|)
condition|)
block|{
name|inIssue
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"MedlinePgn"
argument_list|)
condition|)
block|{
name|inMedlinePgn
operator|=
literal|false
expr_stmt|;
block|}
comment|//pagenumber
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"URL"
argument_list|)
condition|)
block|{
name|inUrl
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Initials"
argument_list|)
condition|)
block|{
comment|//initials= '.' + initials + '.';
name|inInitials
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"AbstractText"
argument_list|)
condition|)
block|{
name|inAbstractText
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"Affiliation"
argument_list|)
condition|)
block|{
name|inAffiliation
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|localName
operator|.
name|equals
argument_list|(
literal|"ArticleId"
argument_list|)
condition|)
block|{
if|if
condition|(
name|inDoi
condition|)
name|inDoi
operator|=
literal|false
expr_stmt|;
elseif|else
if|if
condition|(
name|inPii
condition|)
name|inPii
operator|=
literal|false
expr_stmt|;
block|}
block|}
DECL|method|characters ( char[] data, int start, int length )
specifier|public
name|void
name|characters
parameter_list|(
name|char
index|[]
name|data
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|length
parameter_list|)
block|{
comment|// if stack is not ready, data is not content of recognized element
if|if
condition|(
name|inTitle
condition|)
block|{
name|title
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inYear
condition|)
block|{
name|year
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inJournal
condition|)
block|{
name|journal
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inMonth
condition|)
block|{
name|month
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inVolume
condition|)
block|{
name|volume
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inLastName
condition|)
block|{
name|lastname
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inInitials
condition|)
block|{
name|initials
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inIssue
condition|)
block|{
name|number
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inMedlinePgn
condition|)
block|{
name|page
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inMedlineID
condition|)
block|{
name|medlineID
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inURL
condition|)
block|{
name|url
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inPubMedID
condition|)
block|{
name|pubmedid
operator|=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inDescriptorName
condition|)
name|descriptors
operator|.
name|add
argument_list|(
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
argument_list|)
expr_stmt|;
comment|//keywords += new String(data,start,length) + ", ";
elseif|else
if|if
condition|(
name|inForename
condition|)
block|{
name|forename
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
comment|//System.out.println("IN FORENAME: " + forename);
block|}
elseif|else
if|if
condition|(
name|inAbstractText
condition|)
block|{
name|abstractText
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inMedlineDate
condition|)
block|{
name|MedlineDate
operator|+=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inDoi
condition|)
block|{
name|doi
operator|=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inPii
condition|)
block|{
name|pii
operator|=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|inAffiliation
condition|)
block|{
name|affiliation
operator|=
operator|new
name|String
argument_list|(
name|data
argument_list|,
name|start
argument_list|,
name|length
argument_list|)
expr_stmt|;
block|}
block|}
comment|// PENDING jeffrey.kuhn@yale.edu 2005-05-27 : added fixPageRange method
comment|//   Convert medline page ranges from short form to full form.
comment|//   Medline reports page ranges in a shorthand format.
comment|//   The last page is reported using only the digits which
comment|//   differ from the first page.
comment|//      i.e. 12345-51 refers to the actual range 12345-12351
DECL|method|fixPageRange (String pageRange)
specifier|public
name|String
name|fixPageRange
parameter_list|(
name|String
name|pageRange
parameter_list|)
block|{
name|int
name|minusPos
init|=
name|pageRange
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
decl_stmt|;
if|if
condition|(
name|minusPos
operator|<
literal|0
condition|)
block|{
return|return
name|pageRange
return|;
block|}
name|String
name|first
init|=
name|pageRange
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|minusPos
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|last
init|=
name|pageRange
operator|.
name|substring
argument_list|(
name|minusPos
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|int
name|llast
init|=
name|last
operator|.
name|length
argument_list|()
decl_stmt|,
name|lfirst
init|=
name|first
operator|.
name|length
argument_list|()
decl_stmt|;
if|if
condition|(
name|llast
operator|<
name|lfirst
condition|)
block|{
name|last
operator|=
name|first
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lfirst
operator|-
name|llast
argument_list|)
operator|+
name|last
expr_stmt|;
block|}
return|return
name|first
operator|+
literal|"--"
operator|+
name|last
return|;
block|}
block|}
end_class

end_unit

