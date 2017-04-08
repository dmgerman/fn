begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|UnaryOperator
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextFormatter
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|util
operator|.
name|converter
operator|.
name|IntegerStringConverter
import|;
end_import

begin_comment
comment|/**  * Formatter that only accepts integer.  *  * More or less taken from http://stackoverflow.com/a/36749659/873661  */
end_comment

begin_class
DECL|class|OnlyIntegerFormatter
specifier|public
class|class
name|OnlyIntegerFormatter
extends|extends
name|TextFormatter
argument_list|<
name|Integer
argument_list|>
block|{
DECL|method|OnlyIntegerFormatter ()
specifier|public
name|OnlyIntegerFormatter
parameter_list|()
block|{
name|this
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|OnlyIntegerFormatter (Integer defaultValue)
specifier|public
name|OnlyIntegerFormatter
parameter_list|(
name|Integer
name|defaultValue
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|IntegerStringConverter
argument_list|()
argument_list|,
name|defaultValue
argument_list|,
operator|new
name|IntegerFilter
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|class|IntegerFilter
specifier|private
specifier|static
class|class
name|IntegerFilter
implements|implements
name|UnaryOperator
argument_list|<
name|Change
argument_list|>
block|{
DECL|field|DIGIT_PATTERN
specifier|private
specifier|final
specifier|static
name|Pattern
name|DIGIT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\d*"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|apply (TextFormatter.Change aT)
specifier|public
name|Change
name|apply
parameter_list|(
name|TextFormatter
operator|.
name|Change
name|aT
parameter_list|)
block|{
return|return
name|DIGIT_PATTERN
operator|.
name|matcher
argument_list|(
name|aT
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|matches
argument_list|()
condition|?
name|aT
else|:
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit
