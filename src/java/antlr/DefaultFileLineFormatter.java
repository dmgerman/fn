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
DECL|class|DefaultFileLineFormatter
specifier|public
class|class
name|DefaultFileLineFormatter
extends|extends
name|FileLineFormatter
block|{
DECL|method|getFormatString (String fileName, int line)
specifier|public
name|String
name|getFormatString
parameter_list|(
name|String
name|fileName
parameter_list|,
name|int
name|line
parameter_list|)
block|{
if|if
condition|(
name|fileName
operator|!=
literal|null
condition|)
block|{
return|return
name|fileName
operator|+
literal|":"
operator|+
name|line
operator|+
literal|": "
return|;
block|}
else|else
block|{
return|return
literal|"line "
operator|+
name|line
operator|+
literal|": "
return|;
block|}
block|}
block|}
end_class

begin_empty_stmt
empty_stmt|;
end_empty_stmt

end_unit

