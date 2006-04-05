begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Mar 26, 2006  * Time: 8:05:08 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|HTMLConverter
specifier|public
class|class
name|HTMLConverter
implements|implements
name|LayoutFormatter
block|{
DECL|method|format (String text)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|text
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|int
name|c
init|=
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|'&'
condition|)
block|{
name|i
operator|=
name|readHtmlChar
argument_list|(
name|text
argument_list|,
name|sb
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'<'
condition|)
block|{
name|i
operator|=
name|readTag
argument_list|(
name|text
argument_list|,
name|sb
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
else|else
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|field|MAX_TAG_LENGTH
specifier|private
specifier|final
name|int
name|MAX_TAG_LENGTH
init|=
literal|20
decl_stmt|;
DECL|field|MAX_CHAR_LENGTH
specifier|private
specifier|final
name|int
name|MAX_CHAR_LENGTH
init|=
literal|10
decl_stmt|;
DECL|method|readHtmlChar (String text, StringBuffer sb, int position)
specifier|private
name|int
name|readHtmlChar
parameter_list|(
name|String
name|text
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|int
name|position
parameter_list|)
block|{
comment|// Have just read the< character that starts the tag.
name|int
name|index
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|,
name|position
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>
name|position
operator|)
operator|&&
operator|(
name|index
operator|-
name|position
operator|<
name|MAX_CHAR_LENGTH
operator|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Removed code: "
operator|+
name|text
operator|.
name|substring
argument_list|(
name|position
argument_list|,
name|index
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|index
operator|+
literal|1
return|;
comment|// Just skip the tag.
block|}
else|else
return|return
name|position
return|;
comment|// Don't do anything.
block|}
DECL|method|readTag (String text, StringBuffer sb, int position)
specifier|private
name|int
name|readTag
parameter_list|(
name|String
name|text
parameter_list|,
name|StringBuffer
name|sb
parameter_list|,
name|int
name|position
parameter_list|)
block|{
comment|// Have just read the< character that starts the tag.
name|int
name|index
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|'>'
argument_list|,
name|position
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>
name|position
operator|)
operator|&&
operator|(
name|index
operator|-
name|position
operator|<
name|MAX_TAG_LENGTH
operator|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Removed tag: "
operator|+
name|text
operator|.
name|substring
argument_list|(
name|position
argument_list|,
name|index
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|index
operator|+
literal|1
return|;
comment|// Just skip the tag.
block|}
else|else
return|return
name|position
return|;
comment|// Don't do anything.
block|}
block|}
end_class

end_unit

