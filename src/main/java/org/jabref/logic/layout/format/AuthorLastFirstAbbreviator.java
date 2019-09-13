begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Uses as input the fields (author or editor) in the LastFirst format.  *  * This formater enables to abbreviate the authors name in the following way:  *  * Ex: Someone, Van Something will be abbreviated as Someone, V.S.  */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbreviator
specifier|public
class|class
name|AuthorLastFirstAbbreviator
implements|implements
name|LayoutFormatter
block|{
comment|/**      * @see org.jabref.logic.layout.LayoutFormatter#format(java.lang.String)      */
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
comment|// This formatter is a duplicate of AuthorAbbreviator, so we simply call that one.
return|return
operator|new
name|AuthorAbbreviator
argument_list|()
operator|.
name|format
argument_list|(
name|fieldText
argument_list|)
return|;
block|}
block|}
end_class

end_unit

