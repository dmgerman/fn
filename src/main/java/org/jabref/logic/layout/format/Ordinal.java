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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

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
comment|/**  * Converts number to ordinal  */
end_comment

begin_class
DECL|class|Ordinal
specifier|public
class|class
name|Ordinal
implements|implements
name|LayoutFormatter
block|{
comment|// Detect last digit in number not directly followed by a letter plus the number 11
DECL|field|NUMBER_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|NUMBER_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(1?\\d\\b)"
argument_list|)
decl_stmt|;
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
if|if
condition|(
name|fieldText
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|Matcher
name|m
init|=
name|NUMBER_PATTERN
operator|.
name|matcher
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|result
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|int
name|value
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|result
argument_list|)
decl_stmt|;
name|String
name|ordinalString
decl_stmt|;
switch|switch
condition|(
name|value
condition|)
block|{
case|case
literal|1
case|:
name|ordinalString
operator|=
literal|"st"
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|ordinalString
operator|=
literal|"nd"
expr_stmt|;
break|break;
case|case
literal|3
case|:
name|ordinalString
operator|=
literal|"rd"
expr_stmt|;
break|break;
default|default:
name|ordinalString
operator|=
literal|"th"
expr_stmt|;
break|break;
block|}
name|m
operator|.
name|appendReplacement
argument_list|(
name|sb
argument_list|,
name|result
operator|+
name|ordinalString
argument_list|)
expr_stmt|;
block|}
name|m
operator|.
name|appendTail
argument_list|(
name|sb
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

