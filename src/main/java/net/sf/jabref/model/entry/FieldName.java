begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * String constants for BibTeX entry field names  *  */
end_comment

begin_class
DECL|class|FieldName
specifier|public
class|class
name|FieldName
block|{
comment|// Character separating field names that are to be used in sequence as
comment|// fallbacks for a single column (e.g. "author/editor" to use editor where
comment|// author is not set):
DECL|field|FIELD_SEPARATOR
specifier|public
specifier|static
specifier|final
name|String
name|FIELD_SEPARATOR
init|=
literal|"/"
decl_stmt|;
comment|// Field name constants
DECL|field|ABSTRACT
specifier|public
specifier|static
specifier|final
name|String
name|ABSTRACT
init|=
literal|"abstract"
decl_stmt|;
DECL|field|ADDENDUM
specifier|public
specifier|static
specifier|final
name|String
name|ADDENDUM
init|=
literal|"addendum"
decl_stmt|;
DECL|field|ADDRESS
specifier|public
specifier|static
specifier|final
name|String
name|ADDRESS
init|=
literal|"address"
decl_stmt|;
DECL|field|ANNOTE
specifier|public
specifier|static
specifier|final
name|String
name|ANNOTE
init|=
literal|"annote"
decl_stmt|;
DECL|field|ANNOTATION
specifier|public
specifier|static
specifier|final
name|String
name|ANNOTATION
init|=
literal|"annotation"
decl_stmt|;
DECL|field|ASSIGNEE
specifier|public
specifier|static
specifier|final
name|String
name|ASSIGNEE
init|=
literal|"assignee"
decl_stmt|;
DECL|field|AUTHOR
specifier|public
specifier|static
specifier|final
name|String
name|AUTHOR
init|=
literal|"author"
decl_stmt|;
DECL|field|BOOKAUTHOR
specifier|public
specifier|static
specifier|final
name|String
name|BOOKAUTHOR
init|=
literal|"bookauthor"
decl_stmt|;
DECL|field|BOOKPAGINATION
specifier|public
specifier|static
specifier|final
name|String
name|BOOKPAGINATION
init|=
literal|"bookpagination"
decl_stmt|;
DECL|field|BOOKSUBTITLE
specifier|public
specifier|static
specifier|final
name|String
name|BOOKSUBTITLE
init|=
literal|"booksubtitle"
decl_stmt|;
DECL|field|BOOKTITLE
specifier|public
specifier|static
specifier|final
name|String
name|BOOKTITLE
init|=
literal|"booktitle"
decl_stmt|;
DECL|field|BOOKTITLEADDON
specifier|public
specifier|static
specifier|final
name|String
name|BOOKTITLEADDON
init|=
literal|"booktitleaddon"
decl_stmt|;
DECL|field|CHAPTER
specifier|public
specifier|static
specifier|final
name|String
name|CHAPTER
init|=
literal|"chapter"
decl_stmt|;
DECL|field|COMMENTS
specifier|public
specifier|static
specifier|final
name|String
name|COMMENTS
init|=
literal|"comments"
decl_stmt|;
DECL|field|CROSSREF
specifier|public
specifier|static
specifier|final
name|String
name|CROSSREF
init|=
literal|"crossref"
decl_stmt|;
DECL|field|DATE
specifier|public
specifier|static
specifier|final
name|String
name|DATE
init|=
literal|"date"
decl_stmt|;
DECL|field|DAY
specifier|public
specifier|static
specifier|final
name|String
name|DAY
init|=
literal|"day"
decl_stmt|;
DECL|field|DAYFILED
specifier|public
specifier|static
specifier|final
name|String
name|DAYFILED
init|=
literal|"dayfiled"
decl_stmt|;
DECL|field|DOI
specifier|public
specifier|static
specifier|final
name|String
name|DOI
init|=
literal|"doi"
decl_stmt|;
DECL|field|EDITION
specifier|public
specifier|static
specifier|final
name|String
name|EDITION
init|=
literal|"edition"
decl_stmt|;
DECL|field|EDITOR
specifier|public
specifier|static
specifier|final
name|String
name|EDITOR
init|=
literal|"editor"
decl_stmt|;
DECL|field|EID
specifier|public
specifier|static
specifier|final
name|String
name|EID
init|=
literal|"eid"
decl_stmt|;
DECL|field|ENTRYSET
specifier|public
specifier|static
specifier|final
name|String
name|ENTRYSET
init|=
literal|"entryset"
decl_stmt|;
DECL|field|EPRINT
specifier|public
specifier|static
specifier|final
name|String
name|EPRINT
init|=
literal|"eprint"
decl_stmt|;
DECL|field|EPRINTCLASS
specifier|public
specifier|static
specifier|final
name|String
name|EPRINTCLASS
init|=
literal|"eprintclass"
decl_stmt|;
DECL|field|EPRINTTYPE
specifier|public
specifier|static
specifier|final
name|String
name|EPRINTTYPE
init|=
literal|"eprinttype"
decl_stmt|;
DECL|field|EVENTTITLE
specifier|public
specifier|static
specifier|final
name|String
name|EVENTTITLE
init|=
literal|"eventtitle"
decl_stmt|;
DECL|field|FILE
specifier|public
specifier|static
specifier|final
name|String
name|FILE
init|=
literal|"file"
decl_stmt|;
DECL|field|FOLDER
specifier|public
specifier|static
specifier|final
name|String
name|FOLDER
init|=
literal|"folder"
decl_stmt|;
DECL|field|GENDER
specifier|public
specifier|static
specifier|final
name|String
name|GENDER
init|=
literal|"gender"
decl_stmt|;
DECL|field|HOLDER
specifier|public
specifier|static
specifier|final
name|String
name|HOLDER
init|=
literal|"holder"
decl_stmt|;
DECL|field|HOWPUBLISHED
specifier|public
specifier|static
specifier|final
name|String
name|HOWPUBLISHED
init|=
literal|"howpublished"
decl_stmt|;
DECL|field|INSTITUTION
specifier|public
specifier|static
specifier|final
name|String
name|INSTITUTION
init|=
literal|"institution"
decl_stmt|;
DECL|field|ISBN
specifier|public
specifier|static
specifier|final
name|String
name|ISBN
init|=
literal|"isbn"
decl_stmt|;
DECL|field|ISRN
specifier|public
specifier|static
specifier|final
name|String
name|ISRN
init|=
literal|"isrn"
decl_stmt|;
DECL|field|ISSN
specifier|public
specifier|static
specifier|final
name|String
name|ISSN
init|=
literal|"issn"
decl_stmt|;
DECL|field|ISSUE
specifier|public
specifier|static
specifier|final
name|String
name|ISSUE
init|=
literal|"issue"
decl_stmt|;
DECL|field|ISSUETITLE
specifier|public
specifier|static
specifier|final
name|String
name|ISSUETITLE
init|=
literal|"issuetitle"
decl_stmt|;
DECL|field|ISSUESUBTITLE
specifier|public
specifier|static
specifier|final
name|String
name|ISSUESUBTITLE
init|=
literal|"issuesubtitle"
decl_stmt|;
DECL|field|JOURNAL
specifier|public
specifier|static
specifier|final
name|String
name|JOURNAL
init|=
literal|"journal"
decl_stmt|;
DECL|field|JOURNALSUBTITLE
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALSUBTITLE
init|=
literal|"journalsubtitle"
decl_stmt|;
DECL|field|JOURNALTITLE
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALTITLE
init|=
literal|"journaltitle"
decl_stmt|;
DECL|field|KEY
specifier|public
specifier|static
specifier|final
name|String
name|KEY
init|=
literal|"key"
decl_stmt|;
DECL|field|KEYWORDS
specifier|public
specifier|static
specifier|final
name|String
name|KEYWORDS
init|=
literal|"keywords"
decl_stmt|;
DECL|field|LANGUAGE
specifier|public
specifier|static
specifier|final
name|String
name|LANGUAGE
init|=
literal|"language"
decl_stmt|;
DECL|field|LOCATION
specifier|public
specifier|static
specifier|final
name|String
name|LOCATION
init|=
literal|"location"
decl_stmt|;
DECL|field|MAINSUBTITLE
specifier|public
specifier|static
specifier|final
name|String
name|MAINSUBTITLE
init|=
literal|"mainsubtitle"
decl_stmt|;
DECL|field|MAINTITLE
specifier|public
specifier|static
specifier|final
name|String
name|MAINTITLE
init|=
literal|"maintitle"
decl_stmt|;
DECL|field|MAINTITLEADDON
specifier|public
specifier|static
specifier|final
name|String
name|MAINTITLEADDON
init|=
literal|"maintitleaddon"
decl_stmt|;
DECL|field|MONTH
specifier|public
specifier|static
specifier|final
name|String
name|MONTH
init|=
literal|"month"
decl_stmt|;
DECL|field|MONTHFILED
specifier|public
specifier|static
specifier|final
name|String
name|MONTHFILED
init|=
literal|"monthfiled"
decl_stmt|;
DECL|field|NATIONALITY
specifier|public
specifier|static
specifier|final
name|String
name|NATIONALITY
init|=
literal|"nationality"
decl_stmt|;
DECL|field|NOTE
specifier|public
specifier|static
specifier|final
name|String
name|NOTE
init|=
literal|"note"
decl_stmt|;
DECL|field|NUMBER
specifier|public
specifier|static
specifier|final
name|String
name|NUMBER
init|=
literal|"number"
decl_stmt|;
DECL|field|ORGANIZATION
specifier|public
specifier|static
specifier|final
name|String
name|ORGANIZATION
init|=
literal|"organization"
decl_stmt|;
DECL|field|ORIGLANGUAGE
specifier|public
specifier|static
specifier|final
name|String
name|ORIGLANGUAGE
init|=
literal|"origlanguage"
decl_stmt|;
DECL|field|PAGES
specifier|public
specifier|static
specifier|final
name|String
name|PAGES
init|=
literal|"pages"
decl_stmt|;
DECL|field|PAGETOTAL
specifier|public
specifier|static
specifier|final
name|String
name|PAGETOTAL
init|=
literal|"pagetotal"
decl_stmt|;
DECL|field|PAGINATION
specifier|public
specifier|static
specifier|final
name|String
name|PAGINATION
init|=
literal|"pagination"
decl_stmt|;
DECL|field|PART
specifier|public
specifier|static
specifier|final
name|String
name|PART
init|=
literal|"part"
decl_stmt|;
DECL|field|PDF
specifier|public
specifier|static
specifier|final
name|String
name|PDF
init|=
literal|"pdf"
decl_stmt|;
DECL|field|PMID
specifier|public
specifier|static
specifier|final
name|String
name|PMID
init|=
literal|"pmid"
decl_stmt|;
DECL|field|PS
specifier|public
specifier|static
specifier|final
name|String
name|PS
init|=
literal|"ps"
decl_stmt|;
DECL|field|PUBLISHER
specifier|public
specifier|static
specifier|final
name|String
name|PUBLISHER
init|=
literal|"publisher"
decl_stmt|;
DECL|field|PUBSTATE
specifier|public
specifier|static
specifier|final
name|String
name|PUBSTATE
init|=
literal|"pubstate"
decl_stmt|;
DECL|field|RELATED
specifier|public
specifier|static
specifier|final
name|String
name|RELATED
init|=
literal|"related"
decl_stmt|;
DECL|field|REPORTNO
specifier|public
specifier|static
specifier|final
name|String
name|REPORTNO
init|=
literal|"reportno"
decl_stmt|;
DECL|field|REVIEW
specifier|public
specifier|static
specifier|final
name|String
name|REVIEW
init|=
literal|"review"
decl_stmt|;
DECL|field|REVISION
specifier|public
specifier|static
specifier|final
name|String
name|REVISION
init|=
literal|"revision"
decl_stmt|;
DECL|field|SCHOOL
specifier|public
specifier|static
specifier|final
name|String
name|SCHOOL
init|=
literal|"school"
decl_stmt|;
DECL|field|SERIES
specifier|public
specifier|static
specifier|final
name|String
name|SERIES
init|=
literal|"series"
decl_stmt|;
DECL|field|SUBTITLE
specifier|public
specifier|static
specifier|final
name|String
name|SUBTITLE
init|=
literal|"subtitle"
decl_stmt|;
DECL|field|TITLE
specifier|public
specifier|static
specifier|final
name|String
name|TITLE
init|=
literal|"title"
decl_stmt|;
DECL|field|TITLEADDON
specifier|public
specifier|static
specifier|final
name|String
name|TITLEADDON
init|=
literal|"titleaddon"
decl_stmt|;
DECL|field|TRANSLATOR
specifier|public
specifier|static
specifier|final
name|String
name|TRANSLATOR
init|=
literal|"translator"
decl_stmt|;
DECL|field|TYPE
specifier|public
specifier|static
specifier|final
name|String
name|TYPE
init|=
literal|"type"
decl_stmt|;
DECL|field|URI
specifier|public
specifier|static
specifier|final
name|String
name|URI
init|=
literal|"uri"
decl_stmt|;
DECL|field|URL
specifier|public
specifier|static
specifier|final
name|String
name|URL
init|=
literal|"url"
decl_stmt|;
DECL|field|URLDATE
specifier|public
specifier|static
specifier|final
name|String
name|URLDATE
init|=
literal|"urldate"
decl_stmt|;
DECL|field|VERSION
specifier|public
specifier|static
specifier|final
name|String
name|VERSION
init|=
literal|"version"
decl_stmt|;
DECL|field|VOLUME
specifier|public
specifier|static
specifier|final
name|String
name|VOLUME
init|=
literal|"volume"
decl_stmt|;
DECL|field|VOLUMES
specifier|public
specifier|static
specifier|final
name|String
name|VOLUMES
init|=
literal|"volumes"
decl_stmt|;
DECL|field|YEAR
specifier|public
specifier|static
specifier|final
name|String
name|YEAR
init|=
literal|"year"
decl_stmt|;
DECL|field|YEARFILED
specifier|public
specifier|static
specifier|final
name|String
name|YEARFILED
init|=
literal|"yearfiled"
decl_stmt|;
comment|// IEEE BSTctl fields
DECL|field|CTLALT_STRETCH_FACTOR
specifier|public
specifier|static
specifier|final
name|String
name|CTLALT_STRETCH_FACTOR
init|=
literal|"ctlalt_stretch_factor"
decl_stmt|;
DECL|field|CTLDASH_REPEATED_NAMES
specifier|public
specifier|static
specifier|final
name|String
name|CTLDASH_REPEATED_NAMES
init|=
literal|"ctldash_repeated_names"
decl_stmt|;
DECL|field|CTLMAX_NAMES_FORCED_ETAL
specifier|public
specifier|static
specifier|final
name|String
name|CTLMAX_NAMES_FORCED_ETAL
init|=
literal|"ctlmax_names_forced_etal"
decl_stmt|;
DECL|field|CTLNAME_FORMAT_STRING
specifier|public
specifier|static
specifier|final
name|String
name|CTLNAME_FORMAT_STRING
init|=
literal|"ctlname_format_string"
decl_stmt|;
DECL|field|CTLNAME_LATEX_CMD
specifier|public
specifier|static
specifier|final
name|String
name|CTLNAME_LATEX_CMD
init|=
literal|"ctlname_latex_cmd"
decl_stmt|;
DECL|field|CTLNAME_URL_PREFIX
specifier|public
specifier|static
specifier|final
name|String
name|CTLNAME_URL_PREFIX
init|=
literal|"ctlname_url_prefix"
decl_stmt|;
DECL|field|CTLNAMES_SHOW_ETAL
specifier|public
specifier|static
specifier|final
name|String
name|CTLNAMES_SHOW_ETAL
init|=
literal|"ctlnames_show_etal"
decl_stmt|;
DECL|field|CTLUSE_ALT_SPACING
specifier|public
specifier|static
specifier|final
name|String
name|CTLUSE_ALT_SPACING
init|=
literal|"ctluse_alt_spacing"
decl_stmt|;
DECL|field|CTLUSE_ARTICLE_NUMBER
specifier|public
specifier|static
specifier|final
name|String
name|CTLUSE_ARTICLE_NUMBER
init|=
literal|"ctluse_article_number"
decl_stmt|;
DECL|field|CTLUSE_FORCED_ETAL
specifier|public
specifier|static
specifier|final
name|String
name|CTLUSE_FORCED_ETAL
init|=
literal|"ctluse_forced_etal"
decl_stmt|;
DECL|field|CTLUSE_PAPER
specifier|public
specifier|static
specifier|final
name|String
name|CTLUSE_PAPER
init|=
literal|"ctluse_paper"
decl_stmt|;
DECL|field|CTLUSE_URL
specifier|public
specifier|static
specifier|final
name|String
name|CTLUSE_URL
init|=
literal|"ctluse_url"
decl_stmt|;
comment|// JabRef internal field names
DECL|field|OWNER
specifier|public
specifier|static
specifier|final
name|String
name|OWNER
init|=
literal|"owner"
decl_stmt|;
DECL|field|TIMESTAMP
specifier|public
specifier|static
specifier|final
name|String
name|TIMESTAMP
init|=
literal|"timestamp"
decl_stmt|;
comment|// Not the actual field name, but the default value
DECL|field|NUMBER_COL
specifier|public
specifier|static
specifier|final
name|String
name|NUMBER_COL
init|=
literal|"#"
decl_stmt|;
DECL|field|GROUPS
specifier|public
specifier|static
specifier|final
name|String
name|GROUPS
init|=
literal|"groups"
decl_stmt|;
DECL|field|SEARCH_INTERNAL
specifier|public
specifier|static
specifier|final
name|String
name|SEARCH_INTERNAL
init|=
literal|"__search"
decl_stmt|;
DECL|field|GROUPSEARCH_INTERNAL
specifier|public
specifier|static
specifier|final
name|String
name|GROUPSEARCH_INTERNAL
init|=
literal|"__groupsearch"
decl_stmt|;
DECL|field|MARKED_INTERNAL
specifier|public
specifier|static
specifier|final
name|String
name|MARKED_INTERNAL
init|=
literal|"__markedentry"
decl_stmt|;
comment|// Map to hold alternative display names
DECL|field|displayNames
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|displayNames
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|orFields (String... fields)
specifier|public
specifier|static
name|String
name|orFields
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
block|{
return|return
name|String
operator|.
name|join
argument_list|(
name|FieldName
operator|.
name|FIELD_SEPARATOR
argument_list|,
name|fields
argument_list|)
return|;
block|}
DECL|method|orFields (List<String> fields)
specifier|public
specifier|static
name|String
name|orFields
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
return|return
name|String
operator|.
name|join
argument_list|(
name|FieldName
operator|.
name|FIELD_SEPARATOR
argument_list|,
name|fields
argument_list|)
return|;
block|}
static|static
block|{
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
literal|"DOI"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ISBN
argument_list|,
literal|"ISBN"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ISRN
argument_list|,
literal|"ISRN"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ISSN
argument_list|,
literal|"ISSN"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PMID
argument_list|,
literal|"PMID"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PS
argument_list|,
literal|"PS"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PDF
argument_list|,
literal|"PDF"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URI
argument_list|,
literal|"URI"
argument_list|)
expr_stmt|;
name|displayNames
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
literal|"URL"
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param field - field to get the display version for      * @return A version of the field name more suitable for display      */
DECL|method|getDisplayName (String field)
specifier|public
specifier|static
name|String
name|getDisplayName
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|String
name|lowercaseField
init|=
name|field
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
decl_stmt|;
if|if
condition|(
name|displayNames
operator|.
name|containsKey
argument_list|(
name|lowercaseField
argument_list|)
condition|)
block|{
return|return
name|displayNames
operator|.
name|get
argument_list|(
name|lowercaseField
argument_list|)
return|;
block|}
return|return
name|StringUtil
operator|.
name|capitalizeFirst
argument_list|(
name|field
argument_list|)
return|;
block|}
block|}
end_class

end_unit

