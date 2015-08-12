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
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_class
DECL|class|EasyDateFormat
specifier|public
class|class
name|EasyDateFormat
block|{
comment|/**      * The formatter objects      */
DECL|field|dateFormatter
specifier|private
name|SimpleDateFormat
name|dateFormatter
decl_stmt|;
comment|/**      * Creates a String containing the current date (and possibly time),      * formatted according to the format set in preferences under the key      * "timeStampFormat".      *      * @return The date string.      */
DECL|method|getCurrentDate ()
specifier|public
name|String
name|getCurrentDate
parameter_list|()
block|{
return|return
name|getDateAt
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Creates a readable Date string from the parameter date. The format is set      * in preferences under the key "timeStampFormat".      *      * @return The formatted date string.      */
DECL|method|getDateAt (Date date)
specifier|public
name|String
name|getDateAt
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
comment|// first use, create an instance
if|if
condition|(
name|dateFormatter
operator|==
literal|null
condition|)
block|{
name|String
name|format
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FORMAT
argument_list|)
decl_stmt|;
name|dateFormatter
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
name|format
argument_list|)
expr_stmt|;
block|}
return|return
name|dateFormatter
operator|.
name|format
argument_list|(
name|date
argument_list|)
return|;
block|}
block|}
end_class

end_unit

