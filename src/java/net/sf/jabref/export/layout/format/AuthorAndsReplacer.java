begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Created on 10/10/2004  *   */
end_comment

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
comment|/**  * Replaces and's for& (in case of two authors) and ; (in case  * of more than two authors).  *   * @author Carlos Silla  */
end_comment

begin_class
DECL|class|AuthorAndsReplacer
specifier|public
class|class
name|AuthorAndsReplacer
implements|implements
name|LayoutFormatter
block|{
comment|/* (non-Javadoc) 	 * @see net.sf.jabref.export.layout.LayoutFormatter#format(java.lang.String) 	 */
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
index|[]
name|authors
init|=
name|fieldText
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
decl_stmt|;
name|String
name|s
decl_stmt|;
switch|switch
condition|(
name|authors
operator|.
name|length
condition|)
block|{
case|case
literal|1
case|:
comment|//Does nothing;
name|s
operator|=
name|authors
index|[
literal|0
index|]
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|s
operator|=
name|authors
index|[
literal|0
index|]
operator|+
literal|"& "
operator|+
name|authors
index|[
literal|1
index|]
expr_stmt|;
break|break;
default|default:
name|int
name|i
init|=
literal|0
decl_stmt|,
name|x
init|=
name|authors
operator|.
name|length
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|i
operator|=
literal|0
init|;
name|i
operator|<
name|x
operator|-
literal|2
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"; "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"& "
argument_list|)
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
name|s
operator|=
operator|new
name|String
argument_list|(
name|sb
argument_list|)
expr_stmt|;
break|break;
block|}
return|return
name|s
return|;
block|}
block|}
end_class

end_unit

