begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Formatter that returns the last page from the "pages" field, if set.  *  * For instance, if the pages field is set to "345-360" or "345--360",  * this formatter will return "360".  */
end_comment

begin_class
DECL|class|LastPage
specifier|public
class|class
name|LastPage
implements|implements
name|LayoutFormatter
block|{
DECL|method|format (String s)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
index|[]
name|pageParts
init|=
name|s
operator|.
name|split
argument_list|(
literal|"[\\-]+"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pageParts
operator|.
name|length
operator|==
literal|2
condition|)
return|return
name|pageParts
index|[
literal|1
index|]
return|;
comment|// If we didn't get two parts, it may be that only the number of pages is given.
comment|// Return the first part:
else|else
return|return
name|pageParts
index|[
literal|0
index|]
return|;
comment|//else return "";
block|}
block|}
end_class

end_unit

