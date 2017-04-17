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
name|util
operator|.
name|Optional
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_comment
comment|/**  * Represents a Month of the Year.  */
end_comment

begin_enum
DECL|enum|Month
specifier|public
enum|enum
name|Month
block|{
DECL|enumConstant|JANUARY
name|JANUARY
argument_list|(
literal|"January"
argument_list|,
literal|"jan"
argument_list|,
literal|1
argument_list|)
block|,
DECL|enumConstant|FEBRUARY
name|FEBRUARY
argument_list|(
literal|"February"
argument_list|,
literal|"feb"
argument_list|,
literal|2
argument_list|)
block|,
DECL|enumConstant|MARCH
name|MARCH
argument_list|(
literal|"March"
argument_list|,
literal|"mar"
argument_list|,
literal|3
argument_list|)
block|,
DECL|enumConstant|APRIL
name|APRIL
argument_list|(
literal|"April"
argument_list|,
literal|"apr"
argument_list|,
literal|4
argument_list|)
block|,
DECL|enumConstant|MAY
name|MAY
argument_list|(
literal|"May"
argument_list|,
literal|"may"
argument_list|,
literal|5
argument_list|)
block|,
DECL|enumConstant|JUNE
name|JUNE
argument_list|(
literal|"June"
argument_list|,
literal|"jun"
argument_list|,
literal|6
argument_list|)
block|,
DECL|enumConstant|JULY
name|JULY
argument_list|(
literal|"July"
argument_list|,
literal|"jul"
argument_list|,
literal|7
argument_list|)
block|,
DECL|enumConstant|AUGUST
name|AUGUST
argument_list|(
literal|"August"
argument_list|,
literal|"aug"
argument_list|,
literal|8
argument_list|)
block|,
DECL|enumConstant|SEPTEMBER
name|SEPTEMBER
argument_list|(
literal|"September"
argument_list|,
literal|"sep"
argument_list|,
literal|9
argument_list|)
block|,
DECL|enumConstant|OCTOBER
name|OCTOBER
argument_list|(
literal|"October"
argument_list|,
literal|"oct"
argument_list|,
literal|10
argument_list|)
block|,
DECL|enumConstant|NOVEMBER
name|NOVEMBER
argument_list|(
literal|"November"
argument_list|,
literal|"nov"
argument_list|,
literal|11
argument_list|)
block|,
DECL|enumConstant|DECEMBER
name|DECEMBER
argument_list|(
literal|"December"
argument_list|,
literal|"dec"
argument_list|,
literal|12
argument_list|)
block|;
DECL|field|fullName
specifier|private
specifier|final
name|String
name|fullName
decl_stmt|;
DECL|field|shortName
specifier|private
specifier|final
name|String
name|shortName
decl_stmt|;
DECL|field|twoDigitNumber
specifier|private
specifier|final
name|String
name|twoDigitNumber
decl_stmt|;
DECL|field|number
specifier|private
specifier|final
name|int
name|number
decl_stmt|;
DECL|method|Month (String fullName, String shortName, int number)
name|Month
parameter_list|(
name|String
name|fullName
parameter_list|,
name|String
name|shortName
parameter_list|,
name|int
name|number
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
name|String
operator|.
name|format
argument_list|(
literal|"%02d"
argument_list|,
name|number
argument_list|)
expr_stmt|;
name|this
operator|.
name|number
operator|=
name|number
expr_stmt|;
block|}
comment|/**      * Find month by one-based number.      * If the number is not in the valid range, then an empty Optional is returned.      *      * @param number 1-12 is valid      */
DECL|method|getMonthByNumber (int number)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Month
argument_list|>
name|getMonthByNumber
parameter_list|(
name|int
name|number
parameter_list|)
block|{
for|for
control|(
name|Month
name|month
range|:
name|Month
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|month
operator|.
name|number
operator|==
name|number
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|month
argument_list|)
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * Find month by shortName (3 letters) case insensitive.      * If no matching month is found, then an empty Optional is returned.      *      * @param shortName "jan", "feb", ...      */
DECL|method|getMonthByShortName (String shortName)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Month
argument_list|>
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
name|Month
operator|.
name|values
argument_list|()
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
name|Optional
operator|.
name|of
argument_list|(
name|month
argument_list|)
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * This method accepts three types of months:      * - Single and Double Digit months from 1 to 12 (01 to 12)      * - 3 Digit BibTeX strings (jan, feb, mar...) possibly with # prepended      * - Full English Month identifiers.      *      * @param value the given value      * @return the corresponding Month instance      */
DECL|method|parse (String value)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Month
argument_list|>
name|parse
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|value
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
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
name|Optional
argument_list|<
name|Month
argument_list|>
name|month
init|=
name|Month
operator|.
name|getMonthByShortName
argument_list|(
name|testString
argument_list|)
decl_stmt|;
if|if
condition|(
name|month
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|month
return|;
block|}
try|try
block|{
name|int
name|number
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
argument_list|)
decl_stmt|;
return|return
name|Month
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
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|/**      * Returns the name of a Month in a short (3-letter) format. (jan, feb, mar, ...)      *      * @return 3-letter identifier for a Month      */
DECL|method|getShortName ()
specifier|public
name|String
name|getShortName
parameter_list|()
block|{
return|return
name|shortName
return|;
block|}
comment|/**      * Returns the month in JabRef format. The format is the short 3-digit name surrounded by a '#'.      * Example: #jan#, #feb#, etc.      *      * See https://github.com/JabRef/jabref/issues/263#issuecomment-151246595 for a discussion on that thing.      * This seems to be an<em>invalid</em> format in terms of plain BiBTeX, but a<em>valid</em> format in the case of JabRef      *      * @return Month in JabRef format      */
DECL|method|getJabRefFormat ()
specifier|public
name|String
name|getJabRefFormat
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"#%s#"
argument_list|,
name|shortName
argument_list|)
return|;
block|}
comment|/**      * Returns the number of the Month in a 1-indexed fashion: 1 -> January, 2 -> February etc.      * @return number of the month in the Year      */
DECL|method|getNumber ()
specifier|public
name|int
name|getNumber
parameter_list|()
block|{
return|return
name|number
return|;
block|}
comment|/**      * Returns the name of the long in unabbreviated english.      * @return Month      */
DECL|method|getFullName ()
specifier|public
name|String
name|getFullName
parameter_list|()
block|{
return|return
name|fullName
return|;
block|}
comment|/**      * Returns the number of the Month in a 1-indexed fashion using exactly two digits: 01 -> January, 02 -> February...      * @return number of the month in the Year with two digits      */
DECL|method|getTwoDigitNumber ()
specifier|public
name|String
name|getTwoDigitNumber
parameter_list|()
block|{
return|return
name|twoDigitNumber
return|;
block|}
block|}
end_enum

end_unit

