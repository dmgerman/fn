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
name|EnumSet
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

begin_enum
DECL|enum|FieldProperties
specifier|public
enum|enum
name|FieldProperties
block|{
DECL|enumConstant|YES_NO
name|YES_NO
block|,
DECL|enumConstant|DATE
name|DATE
block|,
DECL|enumConstant|JOURNAL_NAME
name|JOURNAL_NAME
block|,
DECL|enumConstant|EXTERNAL
name|EXTERNAL
block|,
DECL|enumConstant|OWNER
name|OWNER
block|,
DECL|enumConstant|MONTH
name|MONTH
block|,
DECL|enumConstant|FILE_EDITOR
name|FILE_EDITOR
block|,
DECL|enumConstant|NUMERIC
name|NUMERIC
block|,
DECL|enumConstant|PERSON_NAMES
name|PERSON_NAMES
block|,
DECL|enumConstant|INTEGER
name|INTEGER
block|,
DECL|enumConstant|GENDER
name|GENDER
block|,
DECL|enumConstant|LANGUAGE
name|LANGUAGE
block|,
DECL|enumConstant|DOI
name|DOI
block|,
DECL|enumConstant|EDITOR_TYPE
name|EDITOR_TYPE
block|,
DECL|enumConstant|PAGINATION
name|PAGINATION
block|,
DECL|enumConstant|TYPE
name|TYPE
block|,
DECL|enumConstant|CROSSREF
name|CROSSREF
block|,
DECL|enumConstant|ISO_DATE
name|ISO_DATE
block|,
DECL|enumConstant|ISBN
name|ISBN
block|,
DECL|enumConstant|EPRINT
name|EPRINT
block|,
DECL|enumConstant|BOOK_NAME
name|BOOK_NAME
block|,
DECL|enumConstant|SINGLE_ENTRY_LINK
name|SINGLE_ENTRY_LINK
block|,
DECL|enumConstant|MULTIPLE_ENTRY_LINK
name|MULTIPLE_ENTRY_LINK
block|,
DECL|enumConstant|PUBLICATION_STATE
name|PUBLICATION_STATE
block|;
DECL|field|ALL_OPTS
specifier|public
specifier|static
specifier|final
name|Set
argument_list|<
name|FieldProperties
argument_list|>
name|ALL_OPTS
init|=
name|EnumSet
operator|.
name|allOf
argument_list|(
name|FieldProperties
operator|.
name|class
argument_list|)
decl_stmt|;
block|}
end_enum

end_unit

