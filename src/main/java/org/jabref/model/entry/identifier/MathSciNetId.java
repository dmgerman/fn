begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
package|;
end_package

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

begin_comment
comment|/**  * Article identifier for MathSciNet (also sometimes called "MRNumber")  */
end_comment

begin_class
DECL|class|MathSciNetId
specifier|public
class|class
name|MathSciNetId
block|{
DECL|field|identifier
specifier|private
name|String
name|identifier
decl_stmt|;
DECL|method|MathSciNetId (String identifier)
specifier|public
name|MathSciNetId
parameter_list|(
name|String
name|identifier
parameter_list|)
block|{
name|this
operator|.
name|identifier
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|identifier
argument_list|)
expr_stmt|;
block|}
DECL|method|fromString (String mrNumberRaw)
specifier|public
specifier|static
name|MathSciNetId
name|fromString
parameter_list|(
name|String
name|mrNumberRaw
parameter_list|)
block|{
comment|// Take everything before whitespace or open bracket, so something like `619693 (82j:58046)` gets parsed correctly
return|return
operator|new
name|MathSciNetId
argument_list|(
name|StringUtil
operator|.
name|tokenizeToList
argument_list|(
name|mrNumberRaw
argument_list|,
literal|" ("
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|identifier
return|;
block|}
comment|/**      * Get URL in online database.      */
DECL|method|getItemUrl ()
specifier|public
name|String
name|getItemUrl
parameter_list|()
block|{
return|return
literal|"http://www.ams.org/mathscinet-getitem?mr="
operator|+
name|identifier
return|;
block|}
block|}
end_class

end_unit

