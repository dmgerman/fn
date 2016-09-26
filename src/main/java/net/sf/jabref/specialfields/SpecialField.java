begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
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
name|Objects
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
name|javax
operator|.
name|swing
operator|.
name|Icon
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|KeywordList
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

begin_class
DECL|class|SpecialField
specifier|public
specifier|abstract
class|class
name|SpecialField
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
name|SpecialField
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// currently, menuString is used for undo string
comment|// public static String TEXT_UNDO;
DECL|field|values
specifier|private
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
decl_stmt|;
DECL|field|keywords
specifier|private
name|KeywordList
name|keywords
decl_stmt|;
DECL|field|map
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|SpecialFieldValue
argument_list|>
name|map
decl_stmt|;
annotation|@
name|Deprecated
comment|// create via a new constructor SpecialField(List<SpecialFieldValue> values) instead
DECL|method|setValues (List<SpecialFieldValue> values)
specifier|protected
name|void
name|setValues
parameter_list|(
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
parameter_list|)
block|{
name|this
operator|.
name|values
operator|=
name|values
expr_stmt|;
name|this
operator|.
name|keywords
operator|=
operator|new
name|KeywordList
argument_list|()
expr_stmt|;
name|this
operator|.
name|map
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
expr_stmt|;
for|for
control|(
name|SpecialFieldValue
name|value
range|:
name|values
control|)
block|{
name|value
operator|.
name|getKeyword
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|keywords
operator|::
name|add
argument_list|)
expr_stmt|;
name|value
operator|.
name|getFieldValue
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
name|map
operator|.
name|put
argument_list|(
name|fieldValue
argument_list|,
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getValues ()
specifier|public
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|this
operator|.
name|values
return|;
block|}
DECL|method|getKeyWords ()
specifier|public
name|KeywordList
name|getKeyWords
parameter_list|()
block|{
return|return
name|this
operator|.
name|keywords
return|;
block|}
DECL|method|parse (String s)
specifier|public
name|Optional
argument_list|<
name|SpecialFieldValue
argument_list|>
name|parse
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|map
operator|.
name|get
argument_list|(
name|s
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getFieldName ()
specifier|public
specifier|abstract
name|String
name|getFieldName
parameter_list|()
function_decl|;
DECL|method|getLocalizedFieldName ()
specifier|public
specifier|abstract
name|String
name|getLocalizedFieldName
parameter_list|()
function_decl|;
DECL|method|getRepresentingIcon ()
specifier|public
specifier|abstract
name|Icon
name|getRepresentingIcon
parameter_list|()
function_decl|;
DECL|method|getMenuString ()
specifier|public
name|String
name|getMenuString
parameter_list|()
block|{
return|return
name|getLocalizedFieldName
argument_list|()
return|;
block|}
DECL|method|getToolTip ()
specifier|public
name|String
name|getToolTip
parameter_list|()
block|{
return|return
name|getLocalizedFieldName
argument_list|()
return|;
block|}
DECL|method|getTextDone (String... params)
specifier|public
name|String
name|getTextDone
parameter_list|(
name|String
modifier|...
name|params
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|params
argument_list|)
expr_stmt|;
if|if
condition|(
name|isSingleValueField
argument_list|()
operator|&&
operator|(
name|params
operator|.
name|length
operator|==
literal|1
operator|)
operator|&&
operator|(
name|params
index|[
literal|0
index|]
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// Single value fields can be toggled only
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggled '%0' for %1 entries"
argument_list|,
name|getLocalizedFieldName
argument_list|()
argument_list|,
name|params
index|[
literal|0
index|]
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|isSingleValueField
argument_list|()
operator|&&
operator|(
name|params
operator|.
name|length
operator|==
literal|2
operator|)
operator|&&
operator|(
name|params
index|[
literal|0
index|]
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|params
index|[
literal|1
index|]
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// setting a multi value special field - the setted value is displayed, too
name|String
index|[]
name|allParams
init|=
block|{
name|getLocalizedFieldName
argument_list|()
block|,
name|params
index|[
literal|0
index|]
block|,
name|params
index|[
literal|1
index|]
block|}
decl_stmt|;
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set '%0' to '%1' for %2 entries"
argument_list|,
name|allParams
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|isSingleValueField
argument_list|()
operator|&&
operator|(
name|params
operator|.
name|length
operator|==
literal|1
operator|)
operator|&&
operator|(
name|params
index|[
literal|0
index|]
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// clearing a multi value specialfield
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cleared '%0' for %1 entries"
argument_list|,
name|getLocalizedFieldName
argument_list|()
argument_list|,
name|params
index|[
literal|0
index|]
argument_list|)
return|;
block|}
else|else
block|{
comment|// invalid usage
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Creation of special field status change message failed: illegal argument combination."
argument_list|)
expr_stmt|;
return|return
literal|""
return|;
block|}
block|}
DECL|method|isSingleValueField ()
specifier|public
name|boolean
name|isSingleValueField
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

