begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/RIGHTS.html  *  * $Id$  */
end_comment

begin_class
DECL|class|FileLineFormatter
specifier|public
specifier|abstract
class|class
name|FileLineFormatter
block|{
DECL|field|formatter
specifier|private
specifier|static
name|FileLineFormatter
name|formatter
init|=
operator|new
name|DefaultFileLineFormatter
argument_list|()
decl_stmt|;
DECL|method|getFormatter ()
specifier|public
specifier|static
name|FileLineFormatter
name|getFormatter
parameter_list|()
block|{
return|return
name|formatter
return|;
block|}
DECL|method|setFormatter (FileLineFormatter f)
specifier|public
specifier|static
name|void
name|setFormatter
parameter_list|(
name|FileLineFormatter
name|f
parameter_list|)
block|{
name|formatter
operator|=
name|f
expr_stmt|;
block|}
DECL|method|getFormatString (String fileName, int line)
specifier|public
specifier|abstract
name|String
name|getFormatString
parameter_list|(
name|String
name|fileName
parameter_list|,
name|int
name|line
parameter_list|)
function_decl|;
block|}
end_class

end_unit

