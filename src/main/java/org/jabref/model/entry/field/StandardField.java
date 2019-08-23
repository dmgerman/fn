begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.field
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
package|;
end_package

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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumSet
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

begin_comment
comment|/**  * Standard BibTeX and BibLaTex fields  */
end_comment

begin_enum
DECL|enum|StandardField
specifier|public
enum|enum
name|StandardField
implements|implements
name|Field
block|{
DECL|enumConstant|ABSTRACT
name|ABSTRACT
argument_list|(
literal|"abstract"
argument_list|,
name|FieldProperty
operator|.
name|MULTILINE_TEXT
argument_list|)
block|,
DECL|enumConstant|ADDENDUM
name|ADDENDUM
argument_list|(
literal|"addendum"
argument_list|)
block|,
DECL|enumConstant|ADDRESS
name|ADDRESS
argument_list|(
literal|"address"
argument_list|)
block|,
DECL|enumConstant|AFTERWORD
name|AFTERWORD
argument_list|(
literal|"afterword"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|ANNOTE
name|ANNOTE
argument_list|(
literal|"annote"
argument_list|)
block|,
DECL|enumConstant|ANNOTATION
name|ANNOTATION
argument_list|(
literal|"annotation"
argument_list|)
block|,
DECL|enumConstant|ANNOTATOR
name|ANNOTATOR
argument_list|(
literal|"annotator"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|ARCHIVEPREFIX
name|ARCHIVEPREFIX
argument_list|(
literal|"archiveprefix"
argument_list|)
block|,
DECL|enumConstant|ASSIGNEE
name|ASSIGNEE
argument_list|(
literal|"assignee"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|AUTHOR
name|AUTHOR
argument_list|(
literal|"author"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|BOOKAUTHOR
name|BOOKAUTHOR
argument_list|(
literal|"bookauthor"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|BOOKPAGINATION
name|BOOKPAGINATION
argument_list|(
literal|"bookpagination"
argument_list|,
name|FieldProperty
operator|.
name|PAGINATION
argument_list|)
block|,
DECL|enumConstant|BOOKSUBTITLE
name|BOOKSUBTITLE
argument_list|(
literal|"booksubtitle"
argument_list|,
name|FieldProperty
operator|.
name|BOOK_NAME
argument_list|)
block|,
DECL|enumConstant|BOOKTITLE
name|BOOKTITLE
argument_list|(
literal|"booktitle"
argument_list|,
name|FieldProperty
operator|.
name|BOOK_NAME
argument_list|)
block|,
DECL|enumConstant|BOOKTITLEADDON
name|BOOKTITLEADDON
argument_list|(
literal|"booktitleaddon"
argument_list|)
block|,
DECL|enumConstant|CHAPTER
name|CHAPTER
argument_list|(
literal|"chapter"
argument_list|)
block|,
DECL|enumConstant|COMMENTATOR
name|COMMENTATOR
argument_list|(
literal|"commentator"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|COMMENT
name|COMMENT
argument_list|(
literal|"comment"
argument_list|,
name|FieldProperty
operator|.
name|MULTILINE_TEXT
argument_list|)
block|,
DECL|enumConstant|CROSSREF
name|CROSSREF
argument_list|(
literal|"crossref"
argument_list|,
name|FieldProperty
operator|.
name|SINGLE_ENTRY_LINK
argument_list|)
block|,
DECL|enumConstant|DATE
name|DATE
argument_list|(
literal|"date"
argument_list|,
name|FieldProperty
operator|.
name|DATE
argument_list|)
block|,
DECL|enumConstant|DAY
name|DAY
argument_list|(
literal|"day"
argument_list|)
block|,
DECL|enumConstant|DAYFILED
name|DAYFILED
argument_list|(
literal|"dayfiled"
argument_list|)
block|,
DECL|enumConstant|DOI
name|DOI
argument_list|(
literal|"doi"
argument_list|,
literal|"DOI"
argument_list|,
name|FieldProperty
operator|.
name|DOI
argument_list|)
block|,
DECL|enumConstant|EDITION
name|EDITION
argument_list|(
literal|"edition"
argument_list|,
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
block|,
DECL|enumConstant|EDITOR
name|EDITOR
argument_list|(
literal|"editor"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|EDITORA
name|EDITORA
argument_list|(
literal|"editora"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|EDITORB
name|EDITORB
argument_list|(
literal|"editorb"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|EDITORC
name|EDITORC
argument_list|(
literal|"editorc"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|EDITORTYPE
name|EDITORTYPE
argument_list|(
literal|"editortype"
argument_list|,
name|FieldProperty
operator|.
name|EDITOR_TYPE
argument_list|)
block|,
DECL|enumConstant|EDITORATYPE
name|EDITORATYPE
argument_list|(
literal|"editoratype"
argument_list|,
name|FieldProperty
operator|.
name|EDITOR_TYPE
argument_list|)
block|,
DECL|enumConstant|EDITORBTYPE
name|EDITORBTYPE
argument_list|(
literal|"editorbtype"
argument_list|,
name|FieldProperty
operator|.
name|EDITOR_TYPE
argument_list|)
block|,
DECL|enumConstant|EDITORCTYPE
name|EDITORCTYPE
argument_list|(
literal|"editorctype"
argument_list|,
name|FieldProperty
operator|.
name|EDITOR_TYPE
argument_list|)
block|,
DECL|enumConstant|EID
name|EID
argument_list|(
literal|"eid"
argument_list|)
block|,
DECL|enumConstant|ENTRYSET
name|ENTRYSET
argument_list|(
literal|"entryset"
argument_list|,
name|FieldProperty
operator|.
name|MULTIPLE_ENTRY_LINK
argument_list|)
block|,
DECL|enumConstant|EPRINT
name|EPRINT
argument_list|(
literal|"eprint"
argument_list|,
name|FieldProperty
operator|.
name|EPRINT
argument_list|)
block|,
DECL|enumConstant|EPRINTCLASS
name|EPRINTCLASS
argument_list|(
literal|"eprintclass"
argument_list|)
block|,
DECL|enumConstant|EPRINTTYPE
name|EPRINTTYPE
argument_list|(
literal|"eprinttype"
argument_list|)
block|,
DECL|enumConstant|EVENTDATE
name|EVENTDATE
argument_list|(
literal|"eventdate"
argument_list|,
name|FieldProperty
operator|.
name|DATE
argument_list|)
block|,
DECL|enumConstant|EVENTTITLE
name|EVENTTITLE
argument_list|(
literal|"eventtitle"
argument_list|)
block|,
DECL|enumConstant|EVENTTITLEADDON
name|EVENTTITLEADDON
argument_list|(
literal|"eventtitleaddon"
argument_list|)
block|,
DECL|enumConstant|FILE
name|FILE
argument_list|(
literal|"file"
argument_list|,
name|FieldProperty
operator|.
name|FILE_EDITOR
argument_list|,
name|FieldProperty
operator|.
name|VERBATIM
argument_list|)
block|,
DECL|enumConstant|FOREWORD
name|FOREWORD
argument_list|(
literal|"foreword"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|FOLDER
name|FOLDER
argument_list|(
literal|"folder"
argument_list|)
block|,
DECL|enumConstant|GENDER
name|GENDER
argument_list|(
literal|"gender"
argument_list|,
name|FieldProperty
operator|.
name|GENDER
argument_list|)
block|,
DECL|enumConstant|HOLDER
name|HOLDER
argument_list|(
literal|"holder"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|HOWPUBLISHED
name|HOWPUBLISHED
argument_list|(
literal|"howpublished"
argument_list|)
block|,
DECL|enumConstant|IDS
name|IDS
argument_list|(
literal|"ids"
argument_list|,
name|FieldProperty
operator|.
name|MULTIPLE_ENTRY_LINK
argument_list|)
block|,
DECL|enumConstant|INSTITUTION
name|INSTITUTION
argument_list|(
literal|"institution"
argument_list|)
block|,
DECL|enumConstant|INTRODUCTION
name|INTRODUCTION
argument_list|(
literal|"introduction"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|ISBN
name|ISBN
argument_list|(
literal|"isbn"
argument_list|,
literal|"ISBN"
argument_list|,
name|FieldProperty
operator|.
name|ISBN
argument_list|)
block|,
DECL|enumConstant|ISRN
name|ISRN
argument_list|(
literal|"isrn"
argument_list|,
literal|"ISRN"
argument_list|)
block|,
DECL|enumConstant|ISSN
name|ISSN
argument_list|(
literal|"issn"
argument_list|,
literal|"ISSN"
argument_list|)
block|,
DECL|enumConstant|ISSUE
name|ISSUE
argument_list|(
literal|"issue"
argument_list|)
block|,
DECL|enumConstant|ISSUETITLE
name|ISSUETITLE
argument_list|(
literal|"issuetitle"
argument_list|)
block|,
DECL|enumConstant|ISSUESUBTITLE
name|ISSUESUBTITLE
argument_list|(
literal|"issuesubtitle"
argument_list|)
block|,
DECL|enumConstant|JOURNAL
name|JOURNAL
argument_list|(
literal|"journal"
argument_list|,
name|FieldProperty
operator|.
name|JOURNAL_NAME
argument_list|)
block|,
DECL|enumConstant|JOURNALSUBTITLE
name|JOURNALSUBTITLE
argument_list|(
literal|"journalsubtitle"
argument_list|,
name|FieldProperty
operator|.
name|JOURNAL_NAME
argument_list|)
block|,
DECL|enumConstant|JOURNALTITLE
name|JOURNALTITLE
argument_list|(
literal|"journaltitle"
argument_list|,
name|FieldProperty
operator|.
name|JOURNAL_NAME
argument_list|)
block|,
DECL|enumConstant|KEY
name|KEY
argument_list|(
literal|"key"
argument_list|)
block|,
DECL|enumConstant|KEYWORDS
name|KEYWORDS
argument_list|(
literal|"keywords"
argument_list|)
block|,
DECL|enumConstant|LANGUAGE
name|LANGUAGE
argument_list|(
literal|"language"
argument_list|,
name|FieldProperty
operator|.
name|LANGUAGE
argument_list|)
block|,
DECL|enumConstant|LOCATION
name|LOCATION
argument_list|(
literal|"location"
argument_list|)
block|,
DECL|enumConstant|MAINSUBTITLE
name|MAINSUBTITLE
argument_list|(
literal|"mainsubtitle"
argument_list|,
name|FieldProperty
operator|.
name|BOOK_NAME
argument_list|)
block|,
DECL|enumConstant|MAINTITLE
name|MAINTITLE
argument_list|(
literal|"maintitle"
argument_list|,
name|FieldProperty
operator|.
name|BOOK_NAME
argument_list|)
block|,
DECL|enumConstant|MAINTITLEADDON
name|MAINTITLEADDON
argument_list|(
literal|"maintitleaddon"
argument_list|)
block|,
DECL|enumConstant|MONTH
name|MONTH
argument_list|(
literal|"month"
argument_list|,
name|FieldProperty
operator|.
name|MONTH
argument_list|)
block|,
DECL|enumConstant|MONTHFILED
name|MONTHFILED
argument_list|(
literal|"monthfiled"
argument_list|,
name|FieldProperty
operator|.
name|MONTH
argument_list|)
block|,
DECL|enumConstant|NAMEADDON
name|NAMEADDON
argument_list|(
literal|"nameaddon"
argument_list|)
block|,
DECL|enumConstant|NATIONALITY
name|NATIONALITY
argument_list|(
literal|"nationality"
argument_list|)
block|,
DECL|enumConstant|NOTE
name|NOTE
argument_list|(
literal|"note"
argument_list|)
block|,
DECL|enumConstant|NUMBER
name|NUMBER
argument_list|(
literal|"number"
argument_list|,
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
block|,
DECL|enumConstant|ORGANIZATION
name|ORGANIZATION
argument_list|(
literal|"organization"
argument_list|)
block|,
DECL|enumConstant|ORIGDATE
name|ORIGDATE
argument_list|(
literal|"origdate"
argument_list|,
name|FieldProperty
operator|.
name|DATE
argument_list|)
block|,
DECL|enumConstant|ORIGLANGUAGE
name|ORIGLANGUAGE
argument_list|(
literal|"origlanguage"
argument_list|,
name|FieldProperty
operator|.
name|LANGUAGE
argument_list|)
block|,
DECL|enumConstant|PAGES
name|PAGES
argument_list|(
literal|"pages"
argument_list|,
name|FieldProperty
operator|.
name|PAGES
argument_list|)
block|,
DECL|enumConstant|PAGETOTAL
name|PAGETOTAL
argument_list|(
literal|"pagetotal"
argument_list|)
block|,
DECL|enumConstant|PAGINATION
name|PAGINATION
argument_list|(
literal|"pagination"
argument_list|,
name|FieldProperty
operator|.
name|PAGINATION
argument_list|)
block|,
DECL|enumConstant|PART
name|PART
argument_list|(
literal|"part"
argument_list|)
block|,
DECL|enumConstant|PDF
name|PDF
argument_list|(
literal|"pdf"
argument_list|,
literal|"PDF"
argument_list|)
block|,
DECL|enumConstant|PMID
name|PMID
argument_list|(
literal|"pmid"
argument_list|,
literal|"PMID"
argument_list|,
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
block|,
DECL|enumConstant|PS
name|PS
argument_list|(
literal|"ps"
argument_list|,
literal|"PS"
argument_list|)
block|,
DECL|enumConstant|PUBLISHER
name|PUBLISHER
argument_list|(
literal|"publisher"
argument_list|)
block|,
DECL|enumConstant|PUBSTATE
name|PUBSTATE
argument_list|(
literal|"pubstate"
argument_list|,
name|FieldProperty
operator|.
name|PUBLICATION_STATE
argument_list|)
block|,
DECL|enumConstant|PRIMARYCLASS
name|PRIMARYCLASS
argument_list|(
literal|"primaryclass"
argument_list|)
block|,
DECL|enumConstant|RELATED
name|RELATED
argument_list|(
literal|"related"
argument_list|,
name|FieldProperty
operator|.
name|MULTIPLE_ENTRY_LINK
argument_list|)
block|,
DECL|enumConstant|REPORTNO
name|REPORTNO
argument_list|(
literal|"reportno"
argument_list|)
block|,
DECL|enumConstant|REVIEW
name|REVIEW
argument_list|(
literal|"review"
argument_list|)
block|,
DECL|enumConstant|REVISION
name|REVISION
argument_list|(
literal|"revision"
argument_list|)
block|,
DECL|enumConstant|SCHOOL
name|SCHOOL
argument_list|(
literal|"school"
argument_list|)
block|,
DECL|enumConstant|SERIES
name|SERIES
argument_list|(
literal|"series"
argument_list|)
block|,
DECL|enumConstant|SHORTAUTHOR
name|SHORTAUTHOR
argument_list|(
literal|"shortauthor"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|SHORTEDITOR
name|SHORTEDITOR
argument_list|(
literal|"shorteditor"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|SHORTTITLE
name|SHORTTITLE
argument_list|(
literal|"shorttitle"
argument_list|)
block|,
DECL|enumConstant|SORTKEY
name|SORTKEY
argument_list|(
literal|"sortkey"
argument_list|)
block|,
DECL|enumConstant|SORTNAME
name|SORTNAME
argument_list|(
literal|"sortname"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|SUBTITLE
name|SUBTITLE
argument_list|(
literal|"subtitle"
argument_list|)
block|,
DECL|enumConstant|TITLE
name|TITLE
argument_list|(
literal|"title"
argument_list|)
block|,
DECL|enumConstant|TITLEADDON
name|TITLEADDON
argument_list|(
literal|"titleaddon"
argument_list|)
block|,
DECL|enumConstant|TRANSLATOR
name|TRANSLATOR
argument_list|(
literal|"translator"
argument_list|,
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
block|,
DECL|enumConstant|TYPE
name|TYPE
argument_list|(
literal|"type"
argument_list|,
name|FieldProperty
operator|.
name|TYPE
argument_list|)
block|,
DECL|enumConstant|URI
name|URI
argument_list|(
literal|"uri"
argument_list|,
literal|"URI"
argument_list|)
block|,
DECL|enumConstant|URL
name|URL
argument_list|(
literal|"url"
argument_list|,
literal|"URL"
argument_list|,
name|FieldProperty
operator|.
name|EXTERNAL
argument_list|,
name|FieldProperty
operator|.
name|VERBATIM
argument_list|)
block|,
DECL|enumConstant|URLDATE
name|URLDATE
argument_list|(
literal|"urldate"
argument_list|,
name|FieldProperty
operator|.
name|DATE
argument_list|)
block|,
DECL|enumConstant|VENUE
name|VENUE
argument_list|(
literal|"venue"
argument_list|)
block|,
DECL|enumConstant|VERSION
name|VERSION
argument_list|(
literal|"version"
argument_list|)
block|,
DECL|enumConstant|VOLUME
name|VOLUME
argument_list|(
literal|"volume"
argument_list|,
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
block|,
DECL|enumConstant|VOLUMES
name|VOLUMES
argument_list|(
literal|"volumes"
argument_list|,
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
block|,
DECL|enumConstant|YEAR
name|YEAR
argument_list|(
literal|"year"
argument_list|,
name|FieldProperty
operator|.
name|NUMERIC
argument_list|)
block|,
DECL|enumConstant|YEARFILED
name|YEARFILED
argument_list|(
literal|"yearfiled"
argument_list|)
block|,
DECL|enumConstant|MR_NUMBER
name|MR_NUMBER
argument_list|(
literal|"mrnumber"
argument_list|)
block|,
DECL|enumConstant|XDATA
name|XDATA
argument_list|(
literal|"xdata"
argument_list|,
name|FieldProperty
operator|.
name|MULTIPLE_ENTRY_LINK
argument_list|)
block|,
DECL|enumConstant|XREF
name|XREF
argument_list|(
literal|"xref"
argument_list|,
name|FieldProperty
operator|.
name|SINGLE_ENTRY_LINK
argument_list|)
block|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|displayName
specifier|private
specifier|final
name|String
name|displayName
decl_stmt|;
DECL|field|properties
specifier|private
specifier|final
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|properties
decl_stmt|;
DECL|method|StandardField (String name)
name|StandardField
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|displayName
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|properties
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|FieldProperty
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
DECL|method|StandardField (String name, String displayName)
name|StandardField
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|displayName
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|displayName
operator|=
name|displayName
expr_stmt|;
name|this
operator|.
name|properties
operator|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|FieldProperty
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
DECL|method|StandardField (String name, String displayName, FieldProperty first, FieldProperty... rest)
name|StandardField
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|displayName
parameter_list|,
name|FieldProperty
name|first
parameter_list|,
name|FieldProperty
modifier|...
name|rest
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|displayName
operator|=
name|displayName
expr_stmt|;
name|this
operator|.
name|properties
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|first
argument_list|,
name|rest
argument_list|)
expr_stmt|;
block|}
DECL|method|StandardField (String name, FieldProperty first, FieldProperty... rest)
name|StandardField
parameter_list|(
name|String
name|name
parameter_list|,
name|FieldProperty
name|first
parameter_list|,
name|FieldProperty
modifier|...
name|rest
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|displayName
operator|=
literal|null
expr_stmt|;
name|this
operator|.
name|properties
operator|=
name|EnumSet
operator|.
name|of
argument_list|(
name|first
argument_list|,
name|rest
argument_list|)
expr_stmt|;
block|}
DECL|method|fromName (String name)
specifier|public
specifier|static
name|Optional
argument_list|<
name|StandardField
argument_list|>
name|fromName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|Arrays
operator|.
name|stream
argument_list|(
name|StandardField
operator|.
name|values
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|field
lambda|->
name|field
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
argument_list|)
operator|.
name|findAny
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getProperties ()
specifier|public
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|getProperties
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableSet
argument_list|(
name|properties
argument_list|)
return|;
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
name|name
return|;
block|}
annotation|@
name|Override
DECL|method|isStandardField ()
specifier|public
name|boolean
name|isStandardField
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getDisplayName ()
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
if|if
condition|(
name|displayName
operator|==
literal|null
condition|)
block|{
return|return
name|Field
operator|.
name|super
operator|.
name|getDisplayName
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|displayName
return|;
block|}
block|}
block|}
end_enum

end_unit

