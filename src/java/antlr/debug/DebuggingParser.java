begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.debug
package|package
name|antlr
operator|.
name|debug
package|;
end_package

begin_comment
comment|/**  * This type was created in VisualAge.  */
end_comment

begin_interface
DECL|interface|DebuggingParser
specifier|public
interface|interface
name|DebuggingParser
block|{
DECL|method|getRuleName (int n)
specifier|public
name|String
name|getRuleName
parameter_list|(
name|int
name|n
parameter_list|)
function_decl|;
DECL|method|getSemPredName (int n)
specifier|public
name|String
name|getSemPredName
parameter_list|(
name|int
name|n
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

