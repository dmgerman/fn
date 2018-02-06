begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|TextFlow
import|;
end_import

begin_comment
comment|/**  * @author jpf  * @created 11/26/17  */
end_comment

begin_class
DECL|class|TextFlowEqualityHelper
specifier|public
class|class
name|TextFlowEqualityHelper
block|{
DECL|method|checkIfDescriptionEqualsExpectedTexts (TextFlow description, List<Text> expectedTexts)
specifier|public
specifier|static
name|boolean
name|checkIfDescriptionEqualsExpectedTexts
parameter_list|(
name|TextFlow
name|description
parameter_list|,
name|List
argument_list|<
name|Text
argument_list|>
name|expectedTexts
parameter_list|)
block|{
if|if
condition|(
name|expectedTexts
operator|.
name|size
argument_list|()
operator|!=
name|description
operator|.
name|getChildren
argument_list|()
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
name|Text
name|expectedText
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
name|expectedTexts
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|expectedText
operator|=
name|expectedTexts
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// the strings contain not only the text but also the font and other properties
comment|// so comparing them compares the Text object as a whole
comment|// the equals method is not implemented...
if|if
condition|(
operator|!
name|expectedText
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|description
operator|.
name|getChildren
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|checkIfTextsEqualsExpectedTexts (List<Text> texts, List<Text> expectedTexts)
specifier|public
specifier|static
name|boolean
name|checkIfTextsEqualsExpectedTexts
parameter_list|(
name|List
argument_list|<
name|Text
argument_list|>
name|texts
parameter_list|,
name|List
argument_list|<
name|Text
argument_list|>
name|expectedTexts
parameter_list|)
block|{
if|if
condition|(
name|expectedTexts
operator|.
name|size
argument_list|()
operator|!=
name|texts
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
name|Text
name|expectedText
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
name|expectedTexts
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|expectedText
operator|=
name|expectedTexts
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
comment|// the strings contain not only the text but also the font and other properties
comment|// so comparing them compares the Text object as a whole
comment|// the equals method is not implemented...
if|if
condition|(
operator|!
name|expectedText
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|texts
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

