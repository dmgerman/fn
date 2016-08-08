begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|NameFormatterPreferences
specifier|public
class|class
name|NameFormatterPreferences
block|{
DECL|field|nameFormatterKey
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|nameFormatterKey
decl_stmt|;
DECL|field|nameFormatterValue
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|nameFormatterValue
decl_stmt|;
DECL|method|NameFormatterPreferences (List<String> nameFormatterKey, List<String> nameFormatterValue)
specifier|public
name|NameFormatterPreferences
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|nameFormatterKey
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|nameFormatterValue
parameter_list|)
block|{
name|this
operator|.
name|nameFormatterKey
operator|=
name|nameFormatterKey
expr_stmt|;
name|this
operator|.
name|nameFormatterValue
operator|=
name|nameFormatterValue
expr_stmt|;
block|}
DECL|method|fromPreferences (JabRefPreferences jabRefPreferences)
specifier|public
specifier|static
name|NameFormatterPreferences
name|fromPreferences
parameter_list|(
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
block|{
return|return
operator|new
name|NameFormatterPreferences
argument_list|(
name|jabRefPreferences
operator|.
name|getStringList
argument_list|(
name|NameFormatter
operator|.
name|NAME_FORMATER_KEY
argument_list|)
argument_list|,
name|jabRefPreferences
operator|.
name|getStringList
argument_list|(
name|NameFormatter
operator|.
name|NAME_FORMATTER_VALUE
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getNameFormatterKey ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getNameFormatterKey
parameter_list|()
block|{
return|return
name|nameFormatterKey
return|;
block|}
DECL|method|getNameFormatterValue ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getNameFormatterValue
parameter_list|()
block|{
return|return
name|nameFormatterValue
return|;
block|}
block|}
end_class

end_unit

