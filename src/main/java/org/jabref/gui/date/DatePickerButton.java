begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.date
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|date
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|FieldEditor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|lgooddatepicker
operator|.
name|components
operator|.
name|DatePicker
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|lgooddatepicker
operator|.
name|components
operator|.
name|DatePickerSettings
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|lgooddatepicker
operator|.
name|optionalusertools
operator|.
name|DateChangeListener
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|lgooddatepicker
operator|.
name|zinternaltools
operator|.
name|DateChangeEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * wrapper and service class for the DatePicker handling at the EntryEditor  */
end_comment

begin_class
DECL|class|DatePickerButton
specifier|public
class|class
name|DatePickerButton
implements|implements
name|DateChangeListener
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|DatePickerButton
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|datePicker
specifier|private
specifier|final
name|DatePicker
name|datePicker
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|editor
specifier|private
specifier|final
name|FieldEditor
name|editor
decl_stmt|;
DECL|field|dateTimeFormatter
specifier|private
specifier|final
name|DateTimeFormatter
name|dateTimeFormatter
decl_stmt|;
DECL|method|DatePickerButton (FieldEditor pEditor, boolean useIsoFormat)
specifier|public
name|DatePickerButton
parameter_list|(
name|FieldEditor
name|pEditor
parameter_list|,
name|boolean
name|useIsoFormat
parameter_list|)
block|{
if|if
condition|(
name|useIsoFormat
condition|)
block|{
name|dateTimeFormatter
operator|=
name|DateTimeFormatter
operator|.
name|ISO_DATE
expr_stmt|;
block|}
else|else
block|{
name|dateTimeFormatter
operator|=
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
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
argument_list|)
expr_stmt|;
block|}
comment|// Create a date picker with hidden text field (showing button only).
name|DatePickerSettings
name|dateSettings
init|=
operator|new
name|DatePickerSettings
argument_list|()
decl_stmt|;
name|dateSettings
operator|.
name|setVisibleDateTextField
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|dateSettings
operator|.
name|setGapBeforeButtonPixels
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|datePicker
operator|=
operator|new
name|DatePicker
argument_list|(
name|dateSettings
argument_list|)
expr_stmt|;
name|datePicker
operator|.
name|addDateChangeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|datePicker
operator|.
name|getComponentToggleCalendarButton
argument_list|()
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DATE_PICKER
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|datePicker
operator|.
name|getComponentToggleCalendarButton
argument_list|()
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|datePicker
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|editor
operator|=
name|pEditor
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|dateChanged (DateChangeEvent dateChangeEvent)
specifier|public
name|void
name|dateChanged
parameter_list|(
name|DateChangeEvent
name|dateChangeEvent
parameter_list|)
block|{
name|LocalDate
name|date
init|=
name|datePicker
operator|.
name|getDate
argument_list|()
decl_stmt|;
name|String
name|newDate
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|date
operator|!=
literal|null
condition|)
block|{
name|newDate
operator|=
name|dateTimeFormatter
operator|.
name|format
argument_list|(
name|date
operator|.
name|atStartOfDay
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|newDate
operator|.
name|equals
argument_list|(
name|editor
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|editor
operator|.
name|setText
argument_list|(
name|newDate
argument_list|)
expr_stmt|;
block|}
comment|// Set focus to editor component after changing its text:
name|editor
operator|.
name|getTextComponent
argument_list|()
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
DECL|method|getDatePicker ()
specifier|public
name|JComponent
name|getDatePicker
parameter_list|()
block|{
comment|//return datePicker;
return|return
name|panel
return|;
block|}
comment|/**      * Used to set the calender popup to the currently used Date      * @param dateString      */
DECL|method|updateDatePickerDate (String dateString)
specifier|public
name|void
name|updateDatePickerDate
parameter_list|(
name|String
name|dateString
parameter_list|)
block|{
comment|// unregister DateChangeListener before update to prevent circular calls resulting in IllegalStateExceptions
name|datePicker
operator|.
name|removeDateChangeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
name|dateString
operator|!=
literal|null
operator|&&
operator|!
name|dateString
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|datePicker
operator|.
name|setDate
argument_list|(
name|LocalDate
operator|.
name|parse
argument_list|(
name|dateString
argument_list|,
name|dateTimeFormatter
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DateTimeParseException
name|exception
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Unable to parse stored date for field '"
operator|+
name|editor
operator|.
name|getFieldName
argument_list|()
operator|+
literal|"' with current settings. "
operator|+
literal|"Clear button in calender popup will not work."
argument_list|)
expr_stmt|;
block|}
block|}
name|datePicker
operator|.
name|addDateChangeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

