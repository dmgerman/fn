begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_comment
comment|/**  * Converts Entry models from BibTex to biblatex and back.  */
end_comment

begin_class
DECL|class|EntryConverter
specifier|public
class|class
name|EntryConverter
block|{
comment|// BibTeX to biblatex
DECL|field|FIELD_ALIASES_TEX_TO_LTX
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|FIELD_ALIASES_TEX_TO_LTX
decl_stmt|;
comment|// biblatex to BibTeX
DECL|field|FIELD_ALIASES_LTX_TO_TEX
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|FIELD_ALIASES_LTX_TO_TEX
decl_stmt|;
comment|// All aliases
DECL|field|FIELD_ALIASES
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|FIELD_ALIASES
decl_stmt|;
static|static
block|{
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ADDRESS
argument_list|,
name|FieldName
operator|.
name|LOCATION
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|ANNOTE
argument_list|,
name|FieldName
operator|.
name|ANNOTATION
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
literal|"archiveprefix"
argument_list|,
name|FieldName
operator|.
name|EPRINTTYPE
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|FieldName
operator|.
name|JOURNALTITLE
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|KEY
argument_list|,
literal|"sortkey"
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|PDF
argument_list|,
name|FieldName
operator|.
name|FILE
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
literal|"primaryclass"
argument_list|,
name|FieldName
operator|.
name|EPRINTCLASS
argument_list|)
expr_stmt|;
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|put
argument_list|(
name|FieldName
operator|.
name|SCHOOL
argument_list|,
name|FieldName
operator|.
name|INSTITUTION
argument_list|)
expr_stmt|;
comment|// inverse map
name|EntryConverter
operator|.
name|FIELD_ALIASES_LTX_TO_TEX
operator|=
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toMap
argument_list|(
name|Map
operator|.
name|Entry
operator|::
name|getValue
argument_list|,
name|Map
operator|.
name|Entry
operator|::
name|getKey
argument_list|)
argument_list|)
expr_stmt|;
comment|// all aliases
name|FIELD_ALIASES
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
name|FIELD_ALIASES
operator|.
name|putAll
argument_list|(
name|EntryConverter
operator|.
name|FIELD_ALIASES_TEX_TO_LTX
argument_list|)
expr_stmt|;
name|FIELD_ALIASES
operator|.
name|putAll
argument_list|(
name|EntryConverter
operator|.
name|FIELD_ALIASES_LTX_TO_TEX
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
