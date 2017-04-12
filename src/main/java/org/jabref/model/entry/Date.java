begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
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
name|time
operator|.
name|LocalDate
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|Year
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|YearMonth
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeParseException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|temporal
operator|.
name|ChronoField
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|temporal
operator|.
name|TemporalAccessor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_class
DECL|class|Date
specifier|public
class|class
name|Date
block|{
DECL|field|date
specifier|private
specifier|final
name|TemporalAccessor
name|date
decl_stmt|;
DECL|method|Date (TemporalAccessor date)
specifier|public
name|Date
parameter_list|(
name|TemporalAccessor
name|date
parameter_list|)
block|{
name|this
operator|.
name|date
operator|=
name|date
expr_stmt|;
block|}
comment|/**      * Try to parse the following formats      *  - "M/y" (covers 9/15, 9/2015, and 09/2015)      *  - "MMMM (dd), yyyy" (covers September 1, 2015 and September, 2015)      *  - "yyyy-MM-dd" (covers 2009-1-15)      *  - "dd-MM-yyyy" (covers 15-1-2009)      *  - "d.M.uuuu" (covers 15.1.2015)      *  - "uuuu.M.d" (covers 2015.1.15)      * The code is essentially taken from http://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat.      */
DECL|method|parse (String dateString)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Date
argument_list|>
name|parse
parameter_list|(
name|String
name|dateString
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|formatStrings
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"uuuu-M-d"
argument_list|,
literal|"uuuu-M"
argument_list|,
literal|"d-M-uuuu"
argument_list|,
literal|"M/uu"
argument_list|,
literal|"M/uuuu"
argument_list|,
literal|"MMMM d, uuuu"
argument_list|,
literal|"MMMM, uuuu"
argument_list|,
literal|"d.M.uuuu"
argument_list|,
literal|"uuuu.M.d"
argument_list|,
literal|"uuuu"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|formatString
range|:
name|formatStrings
control|)
block|{
try|try
block|{
name|TemporalAccessor
name|parsedDate
init|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
name|formatString
argument_list|)
operator|.
name|parse
argument_list|(
name|dateString
argument_list|)
decl_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|Date
argument_list|(
name|parsedDate
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|DateTimeParseException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|parse (Optional<String> yearValue, Optional<String> monthValue, Optional<String> dayValue)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Date
argument_list|>
name|parse
parameter_list|(
name|Optional
argument_list|<
name|String
argument_list|>
name|yearValue
parameter_list|,
name|Optional
argument_list|<
name|String
argument_list|>
name|monthValue
parameter_list|,
name|Optional
argument_list|<
name|String
argument_list|>
name|dayValue
parameter_list|)
block|{
name|Optional
argument_list|<
name|Year
argument_list|>
name|year
init|=
name|yearValue
operator|.
name|flatMap
argument_list|(
name|Date
operator|::
name|convertToInt
argument_list|)
operator|.
name|map
argument_list|(
name|Year
operator|::
name|of
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|Month
argument_list|>
name|month
init|=
name|monthValue
operator|.
name|flatMap
argument_list|(
name|Month
operator|::
name|parse
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|Integer
argument_list|>
name|day
init|=
name|dayValue
operator|.
name|flatMap
argument_list|(
name|Date
operator|::
name|convertToInt
argument_list|)
decl_stmt|;
if|if
condition|(
name|year
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|TemporalAccessor
name|date
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|day
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|date
operator|=
name|LocalDate
operator|.
name|of
argument_list|(
name|year
operator|.
name|get
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|,
name|month
operator|.
name|get
argument_list|()
operator|.
name|getNumber
argument_list|()
argument_list|,
name|day
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|date
operator|=
name|YearMonth
operator|.
name|of
argument_list|(
name|year
operator|.
name|get
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|,
name|month
operator|.
name|get
argument_list|()
operator|.
name|getNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|date
operator|=
name|year
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|Date
argument_list|(
name|date
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|convertToInt (String value)
specifier|private
specifier|static
name|Optional
argument_list|<
name|Integer
argument_list|>
name|convertToInt
parameter_list|(
name|String
name|value
parameter_list|)
block|{
try|try
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|getNormalized ()
specifier|public
name|String
name|getNormalized
parameter_list|()
block|{
name|DateTimeFormatter
name|dateFormatter
init|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
literal|"uuuu[-MM][-dd]"
argument_list|)
decl_stmt|;
return|return
name|dateFormatter
operator|.
name|format
argument_list|(
name|date
argument_list|)
return|;
block|}
DECL|method|getYear ()
specifier|public
name|Optional
argument_list|<
name|Integer
argument_list|>
name|getYear
parameter_list|()
block|{
return|return
name|get
argument_list|(
name|ChronoField
operator|.
name|YEAR
argument_list|)
return|;
block|}
DECL|method|get (ChronoField field)
specifier|public
name|Optional
argument_list|<
name|Integer
argument_list|>
name|get
parameter_list|(
name|ChronoField
name|field
parameter_list|)
block|{
if|if
condition|(
name|date
operator|.
name|isSupported
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|date
operator|.
name|get
argument_list|(
name|field
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
DECL|method|getMonth ()
specifier|public
name|Optional
argument_list|<
name|Integer
argument_list|>
name|getMonth
parameter_list|()
block|{
return|return
name|get
argument_list|(
name|ChronoField
operator|.
name|MONTH_OF_YEAR
argument_list|)
return|;
block|}
DECL|method|getDay ()
specifier|public
name|Optional
argument_list|<
name|Integer
argument_list|>
name|getDay
parameter_list|()
block|{
return|return
name|get
argument_list|(
name|ChronoField
operator|.
name|DAY_OF_MONTH
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|o
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|Date
name|date1
init|=
operator|(
name|Date
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|getYear
argument_list|()
argument_list|,
name|date1
operator|.
name|getYear
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getMonth
argument_list|()
argument_list|,
name|date1
operator|.
name|getMonth
argument_list|()
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|getDay
argument_list|()
argument_list|,
name|date1
operator|.
name|getDay
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Date{"
operator|+
literal|"date="
operator|+
name|date
operator|+
literal|'}'
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|date
argument_list|)
return|;
block|}
block|}
end_class

end_unit

