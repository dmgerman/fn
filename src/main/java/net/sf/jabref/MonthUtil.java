begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|util
operator|.
name|Util
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

begin_comment
comment|/**  * Utility class for everything related to months.  */
end_comment

begin_class
DECL|class|MonthUtil
specifier|public
class|class
name|MonthUtil
block|{
DECL|class|Month
specifier|public
specifier|static
class|class
name|Month
block|{
DECL|field|fullName
specifier|public
specifier|final
name|String
name|fullName
decl_stmt|;
DECL|field|shortName
specifier|public
specifier|final
name|String
name|shortName
decl_stmt|;
DECL|field|twoDigitNumber
specifier|public
specifier|final
name|String
name|twoDigitNumber
decl_stmt|;
DECL|field|bibtexFormat
specifier|public
specifier|final
name|String
name|bibtexFormat
decl_stmt|;
DECL|field|number
specifier|public
specifier|final
name|int
name|number
decl_stmt|;
DECL|field|index
specifier|public
specifier|final
name|int
name|index
decl_stmt|;
DECL|method|Month (String fullName, String shortName, String twoDigitNumber, String bibtexFormat, int number, int index)
specifier|public
name|Month
parameter_list|(
name|String
name|fullName
parameter_list|,
name|String
name|shortName
parameter_list|,
name|String
name|twoDigitNumber
parameter_list|,
name|String
name|bibtexFormat
parameter_list|,
name|int
name|number
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|this
operator|.
name|fullName
operator|=
name|fullName
expr_stmt|;
name|this
operator|.
name|shortName
operator|=
name|shortName
expr_stmt|;
name|this
operator|.
name|twoDigitNumber
operator|=
name|twoDigitNumber
expr_stmt|;
name|this
operator|.
name|bibtexFormat
operator|=
name|bibtexFormat
expr_stmt|;
name|this
operator|.
name|number
operator|=
name|number
expr_stmt|;
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
block|}
DECL|method|isValid ()
specifier|public
name|boolean
name|isValid
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
DECL|class|UnknownMonth
specifier|private
specifier|static
class|class
name|UnknownMonth
extends|extends
name|Month
block|{
DECL|method|UnknownMonth ()
specifier|public
name|UnknownMonth
parameter_list|()
block|{
name|super
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|0
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|isValid ()
specifier|public
name|boolean
name|isValid
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|field|NULL_OBJECT
specifier|private
specifier|static
specifier|final
name|Month
name|NULL_OBJECT
init|=
operator|new
name|UnknownMonth
argument_list|()
decl_stmt|;
DECL|field|months
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|Month
argument_list|>
name|months
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|Month
argument_list|(
literal|"January"
argument_list|,
literal|"jan"
argument_list|,
literal|"01"
argument_list|,
literal|"#jan#"
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"February"
argument_list|,
literal|"feb"
argument_list|,
literal|"02"
argument_list|,
literal|"#feb#"
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"March"
argument_list|,
literal|"mar"
argument_list|,
literal|"03"
argument_list|,
literal|"#mar#"
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"April"
argument_list|,
literal|"apr"
argument_list|,
literal|"04"
argument_list|,
literal|"#apr#"
argument_list|,
literal|4
argument_list|,
literal|3
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"May"
argument_list|,
literal|"may"
argument_list|,
literal|"05"
argument_list|,
literal|"#may#"
argument_list|,
literal|5
argument_list|,
literal|4
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"June"
argument_list|,
literal|"jun"
argument_list|,
literal|"06"
argument_list|,
literal|"#jun#"
argument_list|,
literal|6
argument_list|,
literal|5
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"July"
argument_list|,
literal|"jul"
argument_list|,
literal|"07"
argument_list|,
literal|"#jul#"
argument_list|,
literal|7
argument_list|,
literal|6
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"August"
argument_list|,
literal|"aug"
argument_list|,
literal|"08"
argument_list|,
literal|"#aug#"
argument_list|,
literal|8
argument_list|,
literal|7
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"September"
argument_list|,
literal|"sep"
argument_list|,
literal|"09"
argument_list|,
literal|"#sep#"
argument_list|,
literal|9
argument_list|,
literal|8
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"October"
argument_list|,
literal|"oct"
argument_list|,
literal|"10"
argument_list|,
literal|"#oct#"
argument_list|,
literal|10
argument_list|,
literal|9
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"November"
argument_list|,
literal|"nov"
argument_list|,
literal|"11"
argument_list|,
literal|"#nov#"
argument_list|,
literal|11
argument_list|,
literal|10
argument_list|)
argument_list|,
operator|new
name|Month
argument_list|(
literal|"December"
argument_list|,
literal|"dec"
argument_list|,
literal|"12"
argument_list|,
literal|"#dec#"
argument_list|,
literal|12
argument_list|,
literal|11
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * Find month by number      *      * @param number 1-12 is valid      * @return if valid number -> month.isValid() == true, else otherwise      */
DECL|method|getMonthByNumber (int number)
specifier|public
specifier|static
name|Month
name|getMonthByNumber
parameter_list|(
name|int
name|number
parameter_list|)
block|{
return|return
name|MonthUtil
operator|.
name|getMonthByIndex
argument_list|(
name|number
operator|-
literal|1
argument_list|)
return|;
block|}
comment|/**      * Find month by index      *      * @param index 0-11 is valid      * @return if valid index -> month.isValid() == true, else otherwise      */
DECL|method|getMonthByIndex (int index)
specifier|public
specifier|static
name|Month
name|getMonthByIndex
parameter_list|(
name|int
name|index
parameter_list|)
block|{
for|for
control|(
name|Month
name|month
range|:
name|MonthUtil
operator|.
name|months
control|)
block|{
if|if
condition|(
name|month
operator|.
name|index
operator|==
name|index
condition|)
block|{
return|return
name|month
return|;
block|}
block|}
return|return
name|MonthUtil
operator|.
name|NULL_OBJECT
return|;
block|}
comment|/**      * Find month by shortName (3 letters) case insensitive      *      * @param shortName "jan", "feb", ...      * @return if valid shortName -> month.isValid() == true, else otherwise      */
DECL|method|getMonthByShortName (String shortName)
specifier|public
specifier|static
name|Month
name|getMonthByShortName
parameter_list|(
name|String
name|shortName
parameter_list|)
block|{
for|for
control|(
name|Month
name|month
range|:
name|MonthUtil
operator|.
name|months
control|)
block|{
if|if
condition|(
name|month
operator|.
name|shortName
operator|.
name|equalsIgnoreCase
argument_list|(
name|shortName
argument_list|)
condition|)
block|{
return|return
name|month
return|;
block|}
block|}
return|return
name|MonthUtil
operator|.
name|NULL_OBJECT
return|;
block|}
comment|/**      * This method accepts three types of months given:      * - Single and Double Digit months from 1 to 12 (01 to 12)      * - 3 Digit BibTex strings (jan, feb, mar...)      * - Full English Month identifiers.      *      * @param value the given value      * @return the corresponding Month instance      */
DECL|method|getMonth (String value)
specifier|public
specifier|static
name|Month
name|getMonth
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
return|return
name|MonthUtil
operator|.
name|NULL_OBJECT
return|;
block|}
comment|// Much more liberal matching covering most known abbreviations etc.
name|String
name|testString
init|=
name|value
operator|.
name|replace
argument_list|(
literal|"#"
argument_list|,
literal|""
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|testString
operator|.
name|length
argument_list|()
operator|>
literal|3
condition|)
block|{
name|testString
operator|=
name|testString
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
name|Month
name|m
init|=
name|MonthUtil
operator|.
name|getMonthByShortName
argument_list|(
name|testString
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
return|;
block|}
try|try
block|{
name|int
name|number
init|=
name|Util
operator|.
name|intValueOf
argument_list|(
name|value
argument_list|)
decl_stmt|;
return|return
name|MonthUtil
operator|.
name|getMonthByNumber
argument_list|(
name|number
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
return|return
name|MonthUtil
operator|.
name|NULL_OBJECT
return|;
block|}
block|}
block|}
end_class

end_unit

