begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_class
DECL|class|YearUtil
specifier|public
class|class
name|YearUtil
block|{
DECL|field|CURRENT_YEAR
specifier|private
specifier|static
specifier|final
name|int
name|CURRENT_YEAR
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
decl_stmt|;
comment|/**      * Will convert a two digit year using the following scheme (describe at      * http://www.filemaker.com/help/02-Adding%20and%20view18.html):      *<p/>      * If a two digit year is encountered they are matched against the last 69      * years and future 30 years.      *<p/>      * For instance if it is the year 1992 then entering 23 is taken to be 1923      * but if you enter 23 in 1993 then it will evaluate to 2023.      *      * @param year The year to convert to 4 digits.      * @return      */
DECL|method|toFourDigitYear (String year)
specifier|public
specifier|static
name|String
name|toFourDigitYear
parameter_list|(
name|String
name|year
parameter_list|)
block|{
return|return
name|YearUtil
operator|.
name|toFourDigitYear
argument_list|(
name|year
argument_list|,
name|YearUtil
operator|.
name|CURRENT_YEAR
argument_list|)
return|;
block|}
comment|/**      * Will convert a two digit year using the following scheme (describe at      * http://www.filemaker.com/help/02-Adding%20and%20view18.html):      *<p/>      * If a two digit year is encountered they are matched against the last 69      * years and future 30 years.      *<p/>      * For instance if it is the year 1992 then entering 23 is taken to be 1923      * but if you enter 23 in 1993 then it will evaluate to 2023.      *      * @param year The year to convert to 4 digits.      * @return      */
DECL|method|toFourDigitYear (String year, int thisYear)
specifier|static
name|String
name|toFourDigitYear
parameter_list|(
name|String
name|year
parameter_list|,
name|int
name|thisYear
parameter_list|)
block|{
if|if
condition|(
operator|(
name|year
operator|==
literal|null
operator|)
operator|||
operator|(
name|year
operator|.
name|length
argument_list|()
operator|!=
literal|2
operator|)
condition|)
block|{
return|return
name|year
return|;
block|}
name|Integer
name|yearNumber
init|=
name|intValueOfWithNull
argument_list|(
name|year
argument_list|)
decl_stmt|;
if|if
condition|(
name|yearNumber
operator|==
literal|null
condition|)
block|{
return|return
name|year
return|;
block|}
return|return
name|String
operator|.
name|valueOf
argument_list|(
operator|new
name|Year
argument_list|(
name|thisYear
argument_list|)
operator|.
name|toFourDigitYear
argument_list|(
name|yearNumber
argument_list|)
argument_list|)
return|;
block|}
DECL|method|toFourDigitYearWithInts (String year)
specifier|public
specifier|static
name|int
name|toFourDigitYearWithInts
parameter_list|(
name|String
name|year
parameter_list|)
block|{
return|return
name|YearUtil
operator|.
name|toFourDigitYearWithInts
argument_list|(
name|year
argument_list|,
name|YearUtil
operator|.
name|CURRENT_YEAR
argument_list|)
return|;
block|}
DECL|method|toFourDigitYearWithInts (String year, int thisYear)
specifier|private
specifier|static
name|int
name|toFourDigitYearWithInts
parameter_list|(
name|String
name|year
parameter_list|,
name|int
name|thisYear
parameter_list|)
block|{
if|if
condition|(
operator|(
name|year
operator|==
literal|null
operator|)
operator|||
operator|(
name|year
operator|.
name|length
argument_list|()
operator|!=
literal|2
operator|)
condition|)
block|{
return|return
literal|0
return|;
block|}
name|Integer
name|yearNumber
init|=
name|intValueOfWithNull
argument_list|(
name|year
argument_list|)
decl_stmt|;
if|if
condition|(
name|yearNumber
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
block|}
return|return
operator|new
name|Year
argument_list|(
name|thisYear
argument_list|)
operator|.
name|toFourDigitYear
argument_list|(
name|yearNumber
argument_list|)
return|;
block|}
DECL|method|intValueOfWithNull (String str)
specifier|private
specifier|static
name|Integer
name|intValueOfWithNull
parameter_list|(
name|String
name|str
parameter_list|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|int
name|end
decl_stmt|;
name|boolean
name|sign
init|=
literal|false
decl_stmt|;
name|char
name|ch
decl_stmt|;
if|if
condition|(
operator|(
name|str
operator|==
literal|null
operator|)
operator|||
operator|(
operator|(
name|end
operator|=
name|str
operator|.
name|length
argument_list|()
operator|)
operator|==
literal|0
operator|)
operator|||
operator|(
operator|(
operator|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|<
literal|'0'
operator|)
operator|||
operator|(
name|ch
operator|>
literal|'9'
operator|)
operator|)
operator|&&
operator|(
operator|!
operator|(
name|sign
operator|=
name|ch
operator|==
literal|'-'
operator|)
operator|||
operator|(
operator|++
name|idx
operator|==
name|end
operator|)
operator|||
operator|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
name|idx
argument_list|)
operator|)
operator|<
literal|'0'
operator|)
operator|||
operator|(
name|ch
operator|>
literal|'9'
operator|)
operator|)
operator|)
condition|)
block|{
return|return
literal|null
return|;
block|}
name|int
name|ival
init|=
literal|0
decl_stmt|;
for|for
control|(
init|;
condition|;
name|ival
operator|*=
literal|10
control|)
block|{
name|ival
operator|+=
literal|'0'
operator|-
name|ch
expr_stmt|;
if|if
condition|(
operator|++
name|idx
operator|==
name|end
condition|)
block|{
return|return
name|sign
condition|?
name|ival
else|:
operator|-
name|ival
return|;
block|}
if|if
condition|(
operator|(
operator|(
name|ch
operator|=
name|str
operator|.
name|charAt
argument_list|(
name|idx
argument_list|)
operator|)
operator|<
literal|'0'
operator|)
operator|||
operator|(
name|ch
operator|>
literal|'9'
operator|)
condition|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
DECL|class|Year
specifier|private
specifier|static
class|class
name|Year
block|{
DECL|field|year
specifier|private
specifier|final
name|int
name|year
decl_stmt|;
DECL|field|century
specifier|private
specifier|final
name|int
name|century
decl_stmt|;
DECL|field|yearShort
specifier|private
specifier|final
name|int
name|yearShort
decl_stmt|;
DECL|method|Year (int year)
specifier|public
name|Year
parameter_list|(
name|int
name|year
parameter_list|)
block|{
name|this
operator|.
name|year
operator|=
name|year
expr_stmt|;
name|this
operator|.
name|yearShort
operator|=
name|this
operator|.
name|year
operator|%
literal|100
expr_stmt|;
name|this
operator|.
name|century
operator|=
operator|(
name|this
operator|.
name|year
operator|/
literal|100
operator|)
operator|*
literal|100
expr_stmt|;
block|}
DECL|method|toFourDigitYear (int relativeYear)
specifier|public
name|int
name|toFourDigitYear
parameter_list|(
name|int
name|relativeYear
parameter_list|)
block|{
if|if
condition|(
name|relativeYear
operator|==
name|yearShort
condition|)
block|{
return|return
name|this
operator|.
name|year
return|;
block|}
comment|// 20 , 90
comment|// 99> 30
if|if
condition|(
operator|(
operator|(
operator|(
name|relativeYear
operator|+
literal|100
operator|)
operator|-
name|yearShort
operator|)
operator|%
literal|100
operator|)
operator|>
literal|30
condition|)
block|{
if|if
condition|(
name|relativeYear
operator|<
name|yearShort
condition|)
block|{
return|return
name|century
operator|+
name|relativeYear
return|;
block|}
else|else
block|{
return|return
operator|(
name|century
operator|-
literal|100
operator|)
operator|+
name|relativeYear
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|relativeYear
operator|<
name|yearShort
condition|)
block|{
return|return
name|century
operator|+
literal|100
operator|+
name|relativeYear
return|;
block|}
else|else
block|{
return|return
name|century
operator|+
name|relativeYear
return|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

