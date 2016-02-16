begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|Formatter
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
name|TemporalAccessor
import|;
end_import

begin_comment
comment|/**  * This class transforms date to the format yyyy-mm-dd or yyyy-mm..  */
end_comment

begin_class
DECL|class|DateFormatter
specifier|public
class|class
name|DateFormatter
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
literal|"Date"
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
literal|"DateFormatter"
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
name|TemporalAccessor
name|parsedDate
init|=
name|tryParseDate
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|parsedDate
operator|==
literal|null
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
argument_list|)
return|;
block|}
comment|/*      * Try to parse the following formats      *  "M/y" (covers 9/15, 9/2015, and 09/2015)      *  "MMMM (dd), yyyy" (covers September 1, 2015 and September, 2015)      *  "yyyy-MM-dd" (covers 2009-1-15)      *  "d.M.uuuu" (covers 15.1.2015)      * The code is essentially taken from http://stackoverflow.com/questions/4024544/how-to-parse-dates-in-multiple-formats-using-simpledateformat.      */
DECL|method|tryParseDate (String dateString)
specifier|private
name|TemporalAccessor
name|tryParseDate
parameter_list|(
name|String
name|dateString
parameter_list|)
block|{
name|String
index|[]
name|formatStrings
init|=
block|{
literal|"uuuu-M-d"
block|,
literal|"uuuu-M"
block|,
literal|"M/uu"
block|,
literal|"M/uuuu"
block|,
literal|"MMMM d, uuuu"
block|,
literal|"MMMM, uuuu"
block|,
literal|"d.M.uuuu"
block|}
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
literal|null
return|;
block|}
block|}
end_class

end_unit

