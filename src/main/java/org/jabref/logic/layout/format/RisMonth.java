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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|MonthUtil
import|;
end_import

begin_class
DECL|class|RisMonth
specifier|public
class|class
name|RisMonth
implements|implements
name|LayoutFormatter
block|{
annotation|@
name|Override
DECL|method|format (String month)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|month
parameter_list|)
block|{
if|if
condition|(
name|month
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
name|MonthUtil
operator|.
name|Month
name|m
init|=
name|MonthUtil
operator|.
name|getMonthByShortName
argument_list|(
name|month
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|isValid
argument_list|()
condition|)
block|{
return|return
name|m
operator|.
name|twoDigitNumber
return|;
block|}
else|else
block|{
return|return
name|month
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit
