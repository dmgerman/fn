begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.xmp
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|xmp
package|;
end_package

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
name|Calendar
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
operator|.
name|Entry
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
name|Set
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
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Consumer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Predicate
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
name|TypedBibEntry
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
name|database
operator|.
name|BibDatabaseMode
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
name|FieldName
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
name|apache
operator|.
name|xmpbox
operator|.
name|DateConverter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|xmpbox
operator|.
name|schema
operator|.
name|DublinCoreSchema
import|;
end_import

begin_class
DECL|class|DublinCoreExtractor
specifier|public
class|class
name|DublinCoreExtractor
block|{
DECL|field|dcSchema
specifier|private
specifier|final
name|DublinCoreSchema
name|dcSchema
decl_stmt|;
DECL|field|xmpPreferences
specifier|private
specifier|final
name|XmpPreferences
name|xmpPreferences
decl_stmt|;
DECL|field|bibEntry
specifier|private
specifier|final
name|BibEntry
name|bibEntry
decl_stmt|;
DECL|method|DublinCoreExtractor (DublinCoreSchema dcSchema, XmpPreferences xmpPreferences, BibEntry resolvedEntry)
specifier|public
name|DublinCoreExtractor
parameter_list|(
name|DublinCoreSchema
name|dcSchema
parameter_list|,
name|XmpPreferences
name|xmpPreferences
parameter_list|,
name|BibEntry
name|resolvedEntry
parameter_list|)
block|{
name|this
operator|.
name|dcSchema
operator|=
name|dcSchema
expr_stmt|;
name|this
operator|.
name|xmpPreferences
operator|=
name|xmpPreferences
expr_stmt|;
name|this
operator|.
name|bibEntry
operator|=
name|resolvedEntry
expr_stmt|;
block|}
comment|/**      * Editor in BibTex - Contributor in DublinCore      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractEditor ()
specifier|private
name|void
name|extractEditor
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|contributors
init|=
name|dcSchema
operator|.
name|getContributors
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|contributors
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|contributors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|EDITOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|contributors
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Author in BibTex - Creator in DublinCore      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractAuthor ()
specifier|private
name|void
name|extractAuthor
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|creators
init|=
name|dcSchema
operator|.
name|getCreators
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|creators
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|creators
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|creators
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Year in BibTex - Date in DublinCore is only the year information, because dc interprets empty months as January.      * Tries to extract the month as well.      * In JabRef the bibtex/month/value is prioritized.      *<br/>      * The problem is the default value of the calendar, which is always January, also if there is no month information in      * the xmp metdata. The idea is, to reject all information with YYYY-01-01. In cases, where xmp is written with JabRef      * the month property filled with jan will override this behavior and no data is lost. In the cases, where xmp      * is written by another service, the assumption is, that the 1st January is not a publication date at all.      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractYearAndMonth ()
specifier|private
name|void
name|extractYearAndMonth
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|dates
init|=
name|dcSchema
operator|.
name|getUnqualifiedSequenceValueList
argument_list|(
literal|"date"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dates
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|dates
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|date
init|=
name|dates
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|Calendar
name|calender
init|=
literal|null
decl_stmt|;
try|try
block|{
name|calender
operator|=
name|DateConverter
operator|.
name|toCalendar
argument_list|(
name|date
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
if|if
condition|(
name|calender
operator|!=
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|calender
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
comment|// not the 1st of January
if|if
condition|(
operator|!
operator|(
operator|(
name|calender
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MONTH
argument_list|)
operator|==
literal|0
operator|)
operator|&&
operator|(
name|calender
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_MONTH
argument_list|)
operator|==
literal|1
operator|)
operator|)
condition|)
block|{
name|Optional
argument_list|<
name|Month
argument_list|>
name|month
init|=
name|Month
operator|.
name|getMonthByNumber
argument_list|(
name|calender
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MONTH
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|,
name|month
operator|.
name|get
argument_list|()
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
comment|/**      * Abstract in BibTex - Description in DublinCore      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractAbstract ()
specifier|private
name|void
name|extractAbstract
parameter_list|()
block|{
name|String
name|description
init|=
name|dcSchema
operator|.
name|getDescription
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|description
argument_list|)
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|ABSTRACT
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * DOI in BibTex - Identifier in DublinCore      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractDOI ()
specifier|private
name|void
name|extractDOI
parameter_list|()
block|{
name|String
name|identifier
init|=
name|dcSchema
operator|.
name|getIdentifier
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|identifier
argument_list|)
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|identifier
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Publisher are equivalent in both formats (BibTex and DublinCore)      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractPublisher ()
specifier|private
name|void
name|extractPublisher
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|publishers
init|=
name|dcSchema
operator|.
name|getPublishers
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|publishers
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|publishers
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|PUBLISHER
argument_list|,
name|String
operator|.
name|join
argument_list|(
literal|" and "
argument_list|,
name|publishers
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * This method sets all fields, which are custom in bibtext and therefore supported by jabref, but which are not included in the DublinCore format.      *<p/>      * The relation attribute of DublinCore is abused to insert these custom fields.      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractBibTexFields ()
specifier|private
name|void
name|extractBibTexFields
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|relationships
init|=
name|dcSchema
operator|.
name|getRelations
argument_list|()
decl_stmt|;
name|Predicate
argument_list|<
name|String
argument_list|>
name|isBibTeXElement
init|=
name|s
lambda|->
name|s
operator|.
name|startsWith
argument_list|(
literal|"bibtex/"
argument_list|)
decl_stmt|;
name|Consumer
argument_list|<
name|String
argument_list|>
name|splitBibTeXElement
init|=
name|s
lambda|->
block|{
comment|// the default pattern is bibtex/key/value, but some fields contains url etc.
comment|// so the value property contains additional slashes, which makes the usage of
comment|// String#split complicated.
name|String
name|temp
init|=
name|s
operator|.
name|substring
argument_list|(
literal|"bibtex/"
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|i
init|=
name|temp
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|!=
operator|-
literal|1
condition|)
block|{
name|String
name|key
init|=
name|temp
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|temp
operator|.
name|substring
argument_list|(
name|i
operator|+
literal|1
argument_list|)
decl_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
comment|// only for month field - override value
comment|// workaround, because the date value of the xmp component of pdf box is corrupted
comment|// see also DublinCoreExtractor#extractYearAndMonth
if|if
condition|(
literal|"month"
operator|.
name|equals
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|Month
argument_list|>
name|parsedMonth
init|=
name|Month
operator|.
name|parse
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|parsedMonth
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|key
argument_list|,
name|parsedMonth
operator|.
name|get
argument_list|()
operator|.
name|getShortName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
decl_stmt|;
if|if
condition|(
name|relationships
operator|!=
literal|null
condition|)
block|{
name|relationships
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|isBibTeXElement
argument_list|)
operator|.
name|forEach
argument_list|(
name|splitBibTeXElement
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Rights are equivalent in both formats (BibTex and DublinCore)      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractRights ()
specifier|private
name|void
name|extractRights
parameter_list|()
block|{
name|String
name|rights
init|=
name|dcSchema
operator|.
name|getRights
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|rights
argument_list|)
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"rights"
argument_list|,
name|rights
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Source is equivalent in both formats (BibTex and DublinCore)      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractSource ()
specifier|private
name|void
name|extractSource
parameter_list|()
block|{
name|String
name|source
init|=
name|dcSchema
operator|.
name|getSource
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|source
argument_list|)
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"source"
argument_list|,
name|source
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Keywords in BibTex - Subjects in DublinCore      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractSubject ()
specifier|private
name|void
name|extractSubject
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|subjects
init|=
name|dcSchema
operator|.
name|getSubjects
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|subjects
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|subjects
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|bibEntry
operator|.
name|addKeywords
argument_list|(
name|subjects
argument_list|,
name|xmpPreferences
operator|.
name|getKeywordSeparator
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Title is equivalent in both formats (BibTex and DublinCore)      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractTitle ()
specifier|private
name|void
name|extractTitle
parameter_list|()
block|{
name|String
name|title
init|=
name|dcSchema
operator|.
name|getTitle
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|title
argument_list|)
condition|)
block|{
name|bibEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Type is equivalent in both formats (BibTex and DublinCore)      *      * @param bibEntry The BibEntry object, which is filled during metadata extraction.      * @param dcSchema Metadata in DublinCore format.      */
DECL|method|extractType ()
specifier|private
name|void
name|extractType
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|types
init|=
name|dcSchema
operator|.
name|getTypes
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|types
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|types
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|type
init|=
name|types
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Helper function for retrieving a BibEntry from the DublinCore metadata      * in a PDF file.      *      * To understand how to get hold of a DublinCore have a look in the      * test cases for XMPUtil.      *      * The BibEntry is build by mapping individual fields in the dublin core      * (like creator, title, subject) to fields in a bibtex bibEntry.      *      * @param dcSchema The document information from which to build a BibEntry.      * @return The bibtex bibEntry found in the document information.      */
DECL|method|extractBibtexEntry ()
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|extractBibtexEntry
parameter_list|()
block|{
name|this
operator|.
name|extractEditor
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractAuthor
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractYearAndMonth
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractAbstract
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractDOI
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractPublisher
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractBibTexFields
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractRights
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractSource
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractSubject
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractTitle
argument_list|()
expr_stmt|;
name|this
operator|.
name|extractType
argument_list|()
expr_stmt|;
if|if
condition|(
name|bibEntry
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|)
block|{
name|bibEntry
operator|.
name|setType
argument_list|(
name|BibEntry
operator|.
name|DEFAULT_TYPE
argument_list|)
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|bibEntry
argument_list|)
return|;
block|}
comment|/**      * Bibtex-Fields used: editor, Field: 'dc:contributor'      *      * @param authors      */
DECL|method|fillContributor (String authors)
specifier|private
name|void
name|fillContributor
parameter_list|(
name|String
name|authors
parameter_list|)
block|{
name|AuthorList
name|list
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|authors
argument_list|)
decl_stmt|;
for|for
control|(
name|Author
name|author
range|:
name|list
operator|.
name|getAuthors
argument_list|()
control|)
block|{
name|dcSchema
operator|.
name|addContributor
argument_list|(
name|author
operator|.
name|getFirstLast
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Bibtex-Fields used: author, Field: 'dc:creator'      *      * @param creators      */
DECL|method|fillCreator (String creators)
specifier|private
name|void
name|fillCreator
parameter_list|(
name|String
name|creators
parameter_list|)
block|{
name|AuthorList
name|list
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|creators
argument_list|)
decl_stmt|;
for|for
control|(
name|Author
name|author
range|:
name|list
operator|.
name|getAuthors
argument_list|()
control|)
block|{
name|dcSchema
operator|.
name|addCreator
argument_list|(
name|author
operator|.
name|getFirstLast
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Bibtex-Fields used: year, month, Field: 'dc:date'      */
DECL|method|fillDate ()
specifier|private
name|void
name|fillDate
parameter_list|()
block|{
name|bibEntry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|DATE
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|publicationDate
lambda|->
name|dcSchema
operator|.
name|addUnqualifiedSequenceValue
argument_list|(
literal|"date"
argument_list|,
name|publicationDate
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Bibtex-Fields used: abstract, Field: 'dc:description'      *      * @param description      */
DECL|method|fillDescription (String description)
specifier|private
name|void
name|fillDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|dcSchema
operator|.
name|setDescription
argument_list|(
name|description
argument_list|)
expr_stmt|;
block|}
comment|/**      * Bibtex-Fields used: doi, Field: 'dc:identifier'      *      * @param identifier      */
DECL|method|fillIdentifier (String identifier)
specifier|private
name|void
name|fillIdentifier
parameter_list|(
name|String
name|identifier
parameter_list|)
block|{
name|dcSchema
operator|.
name|setIdentifier
argument_list|(
name|identifier
argument_list|)
expr_stmt|;
block|}
comment|/**      * Bibtex-Fields used: publisher, Field: dc:publisher      *      * @param publisher      */
DECL|method|fillPublisher (String publisher)
specifier|private
name|void
name|fillPublisher
parameter_list|(
name|String
name|publisher
parameter_list|)
block|{
name|dcSchema
operator|.
name|addPublisher
argument_list|(
name|publisher
argument_list|)
expr_stmt|;
block|}
comment|/**      * Bibtex-Fields used: keywords, Field: 'dc:subject'      *      * @param value      */
DECL|method|fillKeywords (String value)
specifier|private
name|void
name|fillKeywords
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|String
index|[]
name|keywords
init|=
name|value
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|keyword
range|:
name|keywords
control|)
block|{
name|dcSchema
operator|.
name|addSubject
argument_list|(
name|keyword
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Bibtex-Fields used: title, Field: 'dc:title'      *      * @param title      */
DECL|method|fillTitle (String title)
specifier|private
name|void
name|fillTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|dcSchema
operator|.
name|setTitle
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
comment|/**      * All others (+ bibtex key) get packaged in the relation attribute      *      * @param key Key of the metadata attribute      * @param value Value of the metadata attribute      */
DECL|method|fillCustomField (String key, String value)
specifier|private
name|void
name|fillCustomField
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|dcSchema
operator|.
name|addRelation
argument_list|(
literal|"bibtex/"
operator|+
name|key
operator|+
literal|'/'
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
DECL|method|fillDublinCoreSchema ()
specifier|public
name|void
name|fillDublinCoreSchema
parameter_list|()
block|{
comment|// Query privacy filter settings
name|boolean
name|useXmpPrivacyFilter
init|=
name|xmpPreferences
operator|.
name|isUseXMPPrivacyFilter
argument_list|()
decl_stmt|;
comment|// Fields for which not to write XMP data later on:
name|Set
argument_list|<
name|String
argument_list|>
name|filters
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|(
name|xmpPreferences
operator|.
name|getXmpPrivacyFilter
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|field
range|:
name|bibEntry
operator|.
name|getFieldMap
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|useXmpPrivacyFilter
operator|&&
name|filters
operator|.
name|contains
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|FieldName
operator|.
name|EDITOR
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillContributor
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|AUTHOR
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillCreator
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|YEAR
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillDate
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|ABSTRACT
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillDescription
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|DOI
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillIdentifier
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|PUBLISHER
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillPublisher
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|KEYWORDS
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillKeywords
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|TITLE
operator|.
name|equals
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|this
operator|.
name|fillTitle
argument_list|(
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|fillCustomField
argument_list|(
name|field
operator|.
name|getKey
argument_list|()
argument_list|,
name|field
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|dcSchema
operator|.
name|setFormat
argument_list|(
literal|"application/pdf"
argument_list|)
expr_stmt|;
comment|// Bibtex-Fields used: entrytype, Field: 'dc:type'
name|TypedBibEntry
name|typedEntry
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|bibEntry
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
decl_stmt|;
name|String
name|o
init|=
name|typedEntry
operator|.
name|getTypeForDisplay
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|o
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|dcSchema
operator|.
name|addType
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

