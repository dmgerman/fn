begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.l10n
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|ResourceBundle
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
comment|/**  * A bundle containing localized strings.  * It wraps an ordinary resource bundle and performs escaping/unescaping of keys and values similar to  * {@link Localization}. Needed to support JavaFX inline binding.  */
end_comment

begin_class
DECL|class|LocalizationBundle
specifier|public
class|class
name|LocalizationBundle
extends|extends
name|ResourceBundle
block|{
DECL|field|baseBundle
specifier|private
specifier|final
name|ResourceBundle
name|baseBundle
decl_stmt|;
DECL|method|LocalizationBundle (ResourceBundle baseBundle)
specifier|public
name|LocalizationBundle
parameter_list|(
name|ResourceBundle
name|baseBundle
parameter_list|)
block|{
name|this
operator|.
name|baseBundle
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|baseBundle
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|handleGetObject (String key)
specifier|protected
name|Object
name|handleGetObject
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|Localization
operator|.
name|translate
argument_list|(
name|baseBundle
argument_list|,
literal|"message"
argument_list|,
name|key
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKeys ()
specifier|public
name|Enumeration
argument_list|<
name|String
argument_list|>
name|getKeys
parameter_list|()
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|baseKeys
init|=
name|Collections
operator|.
name|list
argument_list|(
name|baseBundle
operator|.
name|getKeys
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|unescapedKeys
init|=
name|baseKeys
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|key
lambda|->
operator|new
name|LocalizationKey
argument_list|(
name|key
argument_list|)
operator|.
name|getTranslationValue
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|Collections
operator|.
name|enumeration
argument_list|(
name|unescapedKeys
argument_list|)
return|;
block|}
block|}
end_class

end_unit
