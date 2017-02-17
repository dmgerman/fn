begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

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
name|Optional
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
name|l10n
operator|.
name|Localization
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
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_comment
comment|/**  * This class transforms date to the format yyyy-mm-dd or yyyy-mm..  */
end_comment

begin_class
DECL|class|NormalizeDateFormatter
specifier|public
class|class
name|NormalizeDateFormatter
implements|implements
name|Formatter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Normalize date"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"normalize_date"
return|;
block|}
comment|/**      * Format date string to yyyy-mm-dd or yyyy-mm. Keeps the existing String if it does not match one of the following      * formats:      *  "M/y" (covers 9/15, 9/2015, and 09/2015)      *  "MMMM (dd), yyyy" (covers September 1, 2015 and September, 2015)      *  "yyyy-MM-dd" (covers 2009-1-15)      *  "d.M.uuuu" (covers 15.1.2015)      */
annotation|@
name|Override
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|Optional
argument_list|<
name|TemporalAccessor
argument_list|>
name|parsedDate
init|=
name|tryParseDate
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|parsedDate
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|value
return|;
block|}
name|DateTimeFormatter
name|dateFormatter
init|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
literal|"uuuu-MM[-dd]"
argument_list|)
decl_stmt|;
return|return
name|dateFormatter
operator|.
name|format
argument_list|(
name|parsedDate
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Normalizes the date to ISO date format."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"29.11.2003"
return|;
block|}
comment|/*      * Try to parse the following formats      *  "M/y" (covers 9/15, 9/2015, and 09/2015)      *  "MMMM (dd), yyyy" (covers September 1, 2015 and September, 2015)      *  "yyyy-MM-dd" (covers 2009-1-15)      *  "d.M.uuuu" (covers 15.1.2015)      *  "uuuu.M.d" (covers 2015.1.15)      * The code is essentially taken from http://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat.      */
DECL|method|tryParseDate (String dateString)
specifier|private
name|Optional
argument_list|<
name|TemporalAccessor
argument_list|>
name|tryParseDate
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
return|return
name|Optional
operator|.
name|of
argument_list|(
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
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|defaultHashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|defaultEquals
argument_list|(
name|obj
argument_list|)
return|;
block|}
block|}
end_class

end_unit
