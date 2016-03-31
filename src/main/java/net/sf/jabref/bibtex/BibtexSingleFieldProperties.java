begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
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

begin_enum
DECL|enum|BibtexSingleFieldProperties
specifier|public
enum|enum
name|BibtexSingleFieldProperties
block|{
DECL|enumConstant|YES_NO
name|YES_NO
block|,
DECL|enumConstant|URL
name|URL
block|,
DECL|enumConstant|DATEPICKER
name|DATEPICKER
block|,
DECL|enumConstant|JOURNAL_NAMES
name|JOURNAL_NAMES
block|,
DECL|enumConstant|EXTERNAL
name|EXTERNAL
block|,
DECL|enumConstant|SET_OWNER
name|SET_OWNER
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
block|;
DECL|field|ALL_OPTS
specifier|public
specifier|static
specifier|final
name|EnumSet
argument_list|<
name|BibtexSingleFieldProperties
argument_list|>
name|ALL_OPTS
init|=
name|EnumSet
operator|.
name|allOf
argument_list|(
name|BibtexSingleFieldProperties
operator|.
name|class
argument_list|)
decl_stmt|;
block|}
end_enum

end_unit

