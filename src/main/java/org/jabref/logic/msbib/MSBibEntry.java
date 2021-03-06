begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.msbib
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|msbib
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|Author
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
name|AuthorList
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
name|Month
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
import|;
end_import

begin_comment
comment|/**  * MSBib entry representation  *  * @see<a href="http://mahbub.wordpress.com/2007/03/24/details-of-microsoft-office-2007-bibliographic-format-compared-to-bibtex/">ms office 2007 bibliography format compared to bibtex</a>  * @see<a href="http://mahbub.wordpress.com/2007/03/22/deciphering-microsoft-office-2007-bibliography-format/">deciphering ms office 2007 bibliography format</a>  * @see<a href="http://www.ecma-international.org/publications/standards/Ecma-376.htm">ECMA Standard</a>  */
end_comment

begin_class
DECL|class|MSBibEntry
class|class
name|MSBibEntry
block|{
comment|// MSBib fields and values
DECL|field|fields
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fields
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|authors
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|authors
decl_stmt|;
DECL|field|bookAuthors
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|bookAuthors
decl_stmt|;
DECL|field|editors
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|editors
decl_stmt|;
DECL|field|translators
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|translators
decl_stmt|;
DECL|field|producerNames
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|producerNames
decl_stmt|;
DECL|field|composers
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|composers
decl_stmt|;
DECL|field|conductors
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|conductors
decl_stmt|;
DECL|field|performers
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|performers
decl_stmt|;
DECL|field|writers
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|writers
decl_stmt|;
DECL|field|directors
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|directors
decl_stmt|;
DECL|field|compilers
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|compilers
decl_stmt|;
DECL|field|interviewers
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|interviewers
decl_stmt|;
DECL|field|interviewees
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|interviewees
decl_stmt|;
DECL|field|inventors
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|inventors
decl_stmt|;
DECL|field|counsels
specifier|public
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|counsels
decl_stmt|;
DECL|field|pages
specifier|public
name|PageNumbers
name|pages
decl_stmt|;
DECL|field|standardNumber
specifier|public
name|String
name|standardNumber
decl_stmt|;
DECL|field|address
specifier|public
name|String
name|address
decl_stmt|;
DECL|field|conferenceName
specifier|public
name|String
name|conferenceName
decl_stmt|;
DECL|field|thesisType
specifier|public
name|String
name|thesisType
decl_stmt|;
DECL|field|internetSiteTitle
specifier|public
name|String
name|internetSiteTitle
decl_stmt|;
DECL|field|dateAccessed
specifier|public
name|String
name|dateAccessed
decl_stmt|;
DECL|field|publicationTitle
specifier|public
name|String
name|publicationTitle
decl_stmt|;
DECL|field|albumTitle
specifier|public
name|String
name|albumTitle
decl_stmt|;
DECL|field|broadcastTitle
specifier|public
name|String
name|broadcastTitle
decl_stmt|;
DECL|field|year
specifier|public
name|String
name|year
decl_stmt|;
DECL|field|month
specifier|public
name|String
name|month
decl_stmt|;
DECL|field|day
specifier|public
name|String
name|day
decl_stmt|;
DECL|field|number
specifier|public
name|String
name|number
decl_stmt|;
DECL|field|patentNumber
specifier|public
name|String
name|patentNumber
decl_stmt|;
DECL|field|journalName
specifier|public
name|String
name|journalName
decl_stmt|;
DECL|field|bibtexEntryType
specifier|private
name|String
name|bibtexEntryType
decl_stmt|;
comment|/**      * reduced subset, supports only "CITY , STATE, COUNTRY"<br>      *<b>\b(\w+)\s?[,]?\s?(\w+)\s?[,]?\s?(\w*)\b</b><br>      *  WORD SPACE , SPACE WORD SPACE (Can be zero or more) , SPACE WORD (Can be zero or more)<br>      *  Matches both single locations (only city) like Berlin and full locations like Stroudsburg, PA, USA<br>      *  tested using http://www.regexpal.com/      */
DECL|field|ADDRESS_PATTERN
specifier|private
specifier|final
name|Pattern
name|ADDRESS_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\b(\\w+)\\s?[,]?\\s?(\\w*)\\s?[,]?\\s?(\\w*)\\b"
argument_list|)
decl_stmt|;
DECL|method|MSBibEntry ()
specifier|public
name|MSBibEntry
parameter_list|()
block|{
comment|//empty
block|}
comment|/**      * Createa new {@link MsBibEntry} to import from an xml element      * @param entry      */
DECL|method|MSBibEntry (Element entry)
specifier|public
name|MSBibEntry
parameter_list|(
name|Element
name|entry
parameter_list|)
block|{
name|populateFromXml
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|getType ()
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|fields
operator|.
name|get
argument_list|(
literal|"SourceType"
argument_list|)
return|;
block|}
DECL|method|getCiteKey ()
specifier|public
name|String
name|getCiteKey
parameter_list|()
block|{
return|return
name|fields
operator|.
name|get
argument_list|(
literal|"Tag"
argument_list|)
return|;
block|}
DECL|method|getXmlElementTextContent (String name, Element entry)
specifier|private
name|String
name|getXmlElementTextContent
parameter_list|(
name|String
name|name
parameter_list|,
name|Element
name|entry
parameter_list|)
block|{
name|String
name|value
init|=
literal|null
decl_stmt|;
name|NodeList
name|nodeLst
init|=
name|entry
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|nodeLst
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|value
operator|=
name|nodeLst
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
expr_stmt|;
block|}
return|return
name|value
return|;
block|}
DECL|method|populateFromXml (Element entry)
specifier|private
name|void
name|populateFromXml
parameter_list|(
name|Element
name|entry
parameter_list|)
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
name|entry
operator|.
name|getChildNodes
argument_list|()
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Node
name|node
init|=
name|entry
operator|.
name|getChildNodes
argument_list|()
operator|.
name|item
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|getNodeType
argument_list|()
operator|==
name|Node
operator|.
name|ELEMENT_NODE
condition|)
block|{
name|String
name|key
init|=
name|node
operator|.
name|getLocalName
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|node
operator|.
name|getTextContent
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"SourceType"
operator|.
name|equals
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|this
operator|.
name|bibtexEntryType
operator|=
name|value
expr_stmt|;
block|}
name|fields
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|temp
init|=
name|getXmlElementTextContent
argument_list|(
literal|"Pages"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
if|if
condition|(
name|temp
operator|!=
literal|null
condition|)
block|{
name|pages
operator|=
operator|new
name|PageNumbers
argument_list|(
name|temp
argument_list|)
expr_stmt|;
block|}
name|standardNumber
operator|=
name|getXmlElementTextContent
argument_list|(
literal|"StandardNumber"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|conferenceName
operator|=
name|getXmlElementTextContent
argument_list|(
literal|"ConferenceName"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|String
name|city
init|=
name|getXmlElementTextContent
argument_list|(
literal|"City"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|String
name|state
init|=
name|getXmlElementTextContent
argument_list|(
literal|"StateProvince"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|String
name|country
init|=
name|getXmlElementTextContent
argument_list|(
literal|"CountryRegion"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|StringBuilder
name|addressBuffer
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|city
operator|!=
literal|null
condition|)
block|{
name|addressBuffer
operator|.
name|append
argument_list|(
name|city
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
operator|(
name|state
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|state
operator|.
name|isEmpty
argument_list|()
operator|)
operator|&&
operator|(
operator|(
name|city
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|city
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|addressBuffer
operator|.
name|append
argument_list|(
literal|","
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
name|addressBuffer
operator|.
name|append
argument_list|(
name|state
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|country
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|country
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|addressBuffer
operator|.
name|append
argument_list|(
literal|","
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
name|addressBuffer
operator|.
name|append
argument_list|(
name|country
argument_list|)
expr_stmt|;
block|}
name|address
operator|=
name|addressBuffer
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|address
operator|.
name|isEmpty
argument_list|()
operator|||
literal|","
operator|.
name|equals
argument_list|(
name|address
argument_list|)
condition|)
block|{
name|address
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
literal|"Patent"
operator|.
name|equalsIgnoreCase
argument_list|(
name|bibtexEntryType
argument_list|)
condition|)
block|{
name|number
operator|=
name|getXmlElementTextContent
argument_list|(
literal|"PatentNumber"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
name|journalName
operator|=
name|getXmlElementTextContent
argument_list|(
literal|"JournalName"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|month
operator|=
name|getXmlElementTextContent
argument_list|(
literal|"Month"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|internetSiteTitle
operator|=
name|getXmlElementTextContent
argument_list|(
literal|"InternetSiteTitle"
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|String
name|monthAccessed
init|=
name|getXmlElementTextContent
argument_list|(
literal|"MonthAccessed"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|String
name|dayAccessed
init|=
name|getXmlElementTextContent
argument_list|(
literal|"DayAccessed"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|String
name|yearAccessed
init|=
name|getXmlElementTextContent
argument_list|(
literal|"YearAccessed"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|Date
argument_list|>
name|parsedDateAcessed
init|=
name|Date
operator|.
name|parse
argument_list|(
name|Optional
operator|.
name|ofNullable
argument_list|(
name|yearAccessed
argument_list|)
argument_list|,
name|Optional
operator|.
name|ofNullable
argument_list|(
name|monthAccessed
argument_list|)
argument_list|,
name|Optional
operator|.
name|ofNullable
argument_list|(
name|dayAccessed
argument_list|)
argument_list|)
decl_stmt|;
name|parsedDateAcessed
operator|.
name|map
argument_list|(
name|Date
operator|::
name|getNormalized
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|date
lambda|->
name|dateAccessed
operator|=
name|date
argument_list|)
expr_stmt|;
name|NodeList
name|nodeLst
init|=
name|entry
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Author"
argument_list|)
decl_stmt|;
if|if
condition|(
name|nodeLst
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|getAuthors
argument_list|(
operator|(
name|Element
operator|)
name|nodeLst
operator|.
name|item
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getAuthors (Element authorsElem)
specifier|private
name|void
name|getAuthors
parameter_list|(
name|Element
name|authorsElem
parameter_list|)
block|{
name|authors
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Author"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|bookAuthors
operator|=
name|getSpecificAuthors
argument_list|(
literal|"BookAuthor"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|editors
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Editor"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|translators
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Translator"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|producerNames
operator|=
name|getSpecificAuthors
argument_list|(
literal|"ProducerName"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|composers
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Composer"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|conductors
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Conductor"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|performers
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Performer"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|writers
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Writer"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|directors
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Director"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|compilers
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Compiler"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|interviewers
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Interviewer"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|interviewees
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Interviewee"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|inventors
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Inventor"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
name|counsels
operator|=
name|getSpecificAuthors
argument_list|(
literal|"Counsel"
argument_list|,
name|authorsElem
argument_list|)
expr_stmt|;
block|}
DECL|method|getSpecificAuthors (String type, Element authors)
specifier|private
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|getSpecificAuthors
parameter_list|(
name|String
name|type
parameter_list|,
name|Element
name|authors
parameter_list|)
block|{
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|result
init|=
literal|null
decl_stmt|;
name|NodeList
name|nodeLst
init|=
name|authors
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|nodeLst
operator|.
name|getLength
argument_list|()
operator|<=
literal|0
condition|)
block|{
return|return
name|result
return|;
block|}
name|nodeLst
operator|=
operator|(
operator|(
name|Element
operator|)
name|nodeLst
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"NameList"
argument_list|)
expr_stmt|;
if|if
condition|(
name|nodeLst
operator|.
name|getLength
argument_list|()
operator|<=
literal|0
condition|)
block|{
return|return
name|result
return|;
block|}
name|NodeList
name|person
init|=
operator|(
operator|(
name|Element
operator|)
name|nodeLst
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Person"
argument_list|)
decl_stmt|;
if|if
condition|(
name|person
operator|.
name|getLength
argument_list|()
operator|<=
literal|0
condition|)
block|{
return|return
name|result
return|;
block|}
name|result
operator|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|person
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|NodeList
name|firstName
init|=
operator|(
operator|(
name|Element
operator|)
name|person
operator|.
name|item
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"First"
argument_list|)
decl_stmt|;
name|NodeList
name|lastName
init|=
operator|(
operator|(
name|Element
operator|)
name|person
operator|.
name|item
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Last"
argument_list|)
decl_stmt|;
name|NodeList
name|middleName
init|=
operator|(
operator|(
name|Element
operator|)
name|person
operator|.
name|item
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getElementsByTagNameNS
argument_list|(
literal|"*"
argument_list|,
literal|"Middle"
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|firstName
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|firstName
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|middleName
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|middleName
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|lastName
operator|.
name|getLength
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|lastName
operator|.
name|item
argument_list|(
literal|0
argument_list|)
operator|.
name|getTextContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|AuthorList
name|authorList
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Author
name|author
range|:
name|authorList
operator|.
name|getAuthors
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|MsBibAuthor
argument_list|(
name|author
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**      * Gets the dom representation for one entry, used for export      * @param document XmlDocument      * @return XmlElement represenation of one entry      */
DECL|method|getEntryDom (Document document)
specifier|public
name|Element
name|getEntryDom
parameter_list|(
name|Document
name|document
parameter_list|)
block|{
name|Element
name|rootNode
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
literal|"Source"
argument_list|)
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|fields
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Optional
operator|.
name|ofNullable
argument_list|(
name|dateAccessed
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|field
lambda|->
name|addDateAcessedFields
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|)
argument_list|)
expr_stmt|;
name|Element
name|allAuthors
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
literal|"Author"
argument_list|)
decl_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Author"
argument_list|,
name|authors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"BookAuthor"
argument_list|,
name|bookAuthors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Editor"
argument_list|,
name|editors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Translator"
argument_list|,
name|translators
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"ProducerName"
argument_list|,
name|producerNames
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Composer"
argument_list|,
name|composers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Conductor"
argument_list|,
name|conductors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Performer"
argument_list|,
name|performers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Writer"
argument_list|,
name|writers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Director"
argument_list|,
name|directors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Compiler"
argument_list|,
name|compilers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Interviewer"
argument_list|,
name|interviewers
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Interviewee"
argument_list|,
name|interviewees
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Inventor"
argument_list|,
name|inventors
argument_list|)
expr_stmt|;
name|addAuthor
argument_list|(
name|document
argument_list|,
name|allAuthors
argument_list|,
literal|"Counsel"
argument_list|,
name|counsels
argument_list|)
expr_stmt|;
name|rootNode
operator|.
name|appendChild
argument_list|(
name|allAuthors
argument_list|)
expr_stmt|;
if|if
condition|(
name|pages
operator|!=
literal|null
condition|)
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Pages"
argument_list|,
name|pages
operator|.
name|toString
argument_list|(
literal|"-"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Year"
argument_list|,
name|year
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Month"
argument_list|,
name|month
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Day"
argument_list|,
name|day
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"JournalName"
argument_list|,
name|journalName
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"PatentNumber"
argument_list|,
name|patentNumber
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Number"
argument_list|,
name|number
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"StandardNumber"
argument_list|,
name|standardNumber
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"ConferenceName"
argument_list|,
name|conferenceName
argument_list|)
expr_stmt|;
name|addAddress
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
name|address
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"ThesisType"
argument_list|,
name|thesisType
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"InternetSiteTitle"
argument_list|,
name|internetSiteTitle
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"PublicationTitle"
argument_list|,
name|publicationTitle
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"AlbumTitle"
argument_list|,
name|albumTitle
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"BroadcastTitle"
argument_list|,
name|broadcastTitle
argument_list|)
expr_stmt|;
return|return
name|rootNode
return|;
block|}
DECL|method|addField (Document document, Element parent, String name, String value)
specifier|private
name|void
name|addField
parameter_list|(
name|Document
name|document
parameter_list|,
name|Element
name|parent
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|Element
name|elem
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
name|name
argument_list|)
decl_stmt|;
name|elem
operator|.
name|appendChild
argument_list|(
name|document
operator|.
name|createTextNode
argument_list|(
name|StringUtil
operator|.
name|stripNonValidXMLCharacters
argument_list|(
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|parent
operator|.
name|appendChild
argument_list|(
name|elem
argument_list|)
expr_stmt|;
block|}
comment|//Add authors for export
DECL|method|addAuthor (Document document, Element allAuthors, String entryName, List<MsBibAuthor> authorsLst)
specifier|private
name|void
name|addAuthor
parameter_list|(
name|Document
name|document
parameter_list|,
name|Element
name|allAuthors
parameter_list|,
name|String
name|entryName
parameter_list|,
name|List
argument_list|<
name|MsBibAuthor
argument_list|>
name|authorsLst
parameter_list|)
block|{
if|if
condition|(
name|authorsLst
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|Element
name|authorTop
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
name|entryName
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|MsBibAuthor
argument_list|>
name|personName
init|=
name|authorsLst
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|MsBibAuthor
operator|::
name|isCorporate
argument_list|)
operator|.
name|findFirst
argument_list|()
decl_stmt|;
if|if
condition|(
name|personName
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|MsBibAuthor
name|person
init|=
name|personName
operator|.
name|get
argument_list|()
decl_stmt|;
name|Element
name|corporate
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
literal|"Corporate"
argument_list|)
decl_stmt|;
name|corporate
operator|.
name|setTextContent
argument_list|(
name|person
operator|.
name|getFirstLast
argument_list|()
argument_list|)
expr_stmt|;
name|authorTop
operator|.
name|appendChild
argument_list|(
name|corporate
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Element
name|nameList
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
literal|"NameList"
argument_list|)
decl_stmt|;
for|for
control|(
name|MsBibAuthor
name|name
range|:
name|authorsLst
control|)
block|{
name|Element
name|person
init|=
name|document
operator|.
name|createElementNS
argument_list|(
name|MSBibDatabase
operator|.
name|NAMESPACE
argument_list|,
name|MSBibDatabase
operator|.
name|PREFIX
operator|+
literal|"Person"
argument_list|)
decl_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|person
argument_list|,
literal|"Last"
argument_list|,
name|name
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|person
argument_list|,
literal|"Middle"
argument_list|,
name|name
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|person
argument_list|,
literal|"First"
argument_list|,
name|name
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|nameList
operator|.
name|appendChild
argument_list|(
name|person
argument_list|)
expr_stmt|;
block|}
name|authorTop
operator|.
name|appendChild
argument_list|(
name|nameList
argument_list|)
expr_stmt|;
block|}
name|allAuthors
operator|.
name|appendChild
argument_list|(
name|authorTop
argument_list|)
expr_stmt|;
block|}
DECL|method|addDateAcessedFields (Document document, Element rootNode)
specifier|private
name|void
name|addDateAcessedFields
parameter_list|(
name|Document
name|document
parameter_list|,
name|Element
name|rootNode
parameter_list|)
block|{
name|Optional
argument_list|<
name|Date
argument_list|>
name|parsedDateAcesseField
init|=
name|Date
operator|.
name|parse
argument_list|(
name|dateAccessed
argument_list|)
decl_stmt|;
name|parsedDateAcesseField
operator|.
name|flatMap
argument_list|(
name|Date
operator|::
name|getYear
argument_list|)
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|yearAccessed
lambda|->
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Year"
operator|+
literal|"Accessed"
argument_list|,
name|yearAccessed
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|parsedDateAcesseField
operator|.
name|flatMap
argument_list|(
name|Date
operator|::
name|getMonth
argument_list|)
operator|.
name|map
argument_list|(
name|Month
operator|::
name|getTwoDigitNumber
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|monthAcessed
lambda|->
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Month"
operator|+
literal|"Accessed"
argument_list|,
name|monthAcessed
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|parsedDateAcesseField
operator|.
name|flatMap
argument_list|(
name|Date
operator|::
name|getDay
argument_list|)
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|dayAccessed
lambda|->
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|rootNode
argument_list|,
literal|"Day"
operator|+
literal|"Accessed"
argument_list|,
name|dayAccessed
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|addAddress (Document document, Element parent, String addressToSplit)
specifier|private
name|void
name|addAddress
parameter_list|(
name|Document
name|document
parameter_list|,
name|Element
name|parent
parameter_list|,
name|String
name|addressToSplit
parameter_list|)
block|{
if|if
condition|(
name|addressToSplit
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|Matcher
name|matcher
init|=
name|ADDRESS_PATTERN
operator|.
name|matcher
argument_list|(
name|addressToSplit
argument_list|)
decl_stmt|;
if|if
condition|(
name|addressToSplit
operator|.
name|contains
argument_list|(
literal|","
argument_list|)
operator|&&
name|matcher
operator|.
name|matches
argument_list|()
operator|&&
operator|(
name|matcher
operator|.
name|groupCount
argument_list|()
operator|>=
literal|3
operator|)
condition|)
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|parent
argument_list|,
literal|"City"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|parent
argument_list|,
literal|"StateProvince"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|addField
argument_list|(
name|document
argument_list|,
name|parent
argument_list|,
literal|"CountryRegion"
argument_list|,
name|matcher
operator|.
name|group
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addField
argument_list|(
name|document
argument_list|,
name|parent
argument_list|,
literal|"City"
argument_list|,
name|addressToSplit
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

