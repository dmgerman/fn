begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.push
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
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
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|DialogService
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
name|util
operator|.
name|DefaultTaskExecutor
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
name|util
operator|.
name|FileDialogConfiguration
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_class
DECL|class|PushToApplicationSettings
specifier|public
class|class
name|PushToApplicationSettings
block|{
DECL|field|path
specifier|protected
specifier|final
name|JTextField
name|path
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|field|settings
specifier|protected
name|JPanel
name|settings
decl_stmt|;
DECL|field|builder
specifier|protected
name|FormBuilder
name|builder
decl_stmt|;
DECL|field|application
specifier|protected
name|AbstractPushToApplication
name|application
decl_stmt|;
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
comment|/**      * This method asks the implementing class to return a JPanel populated with the imlementation's options panel, if      * necessary. If the JPanel is shown to the user, and the user indicates that settings should be stored, the      * implementation's storeSettings() method will be called. This method must make sure all widgets in the panel are      * in the correct selection states.      *      * @return a JPanel containing options, or null if options are not needed.      */
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
name|application
operator|.
name|initParameters
argument_list|()
expr_stmt|;
name|String
name|commandPath
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|application
operator|.
name|commandPathPreferenceKey
argument_list|)
decl_stmt|;
if|if
condition|(
name|settings
operator|==
literal|null
condition|)
block|{
name|initSettingsPanel
argument_list|()
expr_stmt|;
block|}
name|path
operator|.
name|setText
argument_list|(
name|commandPath
argument_list|)
expr_stmt|;
return|return
name|settings
return|;
block|}
comment|/**      * Create a FormBuilder, fill it with a textbox for the path and store the JPanel in settings      */
DECL|method|initSettingsPanel ()
specifier|protected
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|builder
operator|=
name|FormBuilder
operator|.
name|create
argument_list|()
expr_stmt|;
name|builder
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref:grow, 4dlu, fill:pref"
argument_list|,
literal|"p"
argument_list|)
argument_list|)
expr_stmt|;
name|StringBuilder
name|label
init|=
operator|new
name|StringBuilder
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to %0"
argument_list|,
name|application
operator|.
name|getApplicationName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
comment|// In case the application name and the actual command is not the same, add the command in brackets
if|if
condition|(
name|application
operator|.
name|getCommandName
argument_list|()
operator|==
literal|null
condition|)
block|{
name|label
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|label
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
operator|.
name|append
argument_list|(
name|application
operator|.
name|getCommandName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"):"
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|add
argument_list|(
name|label
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|path
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|path
operator|.
name|setText
argument_list|(
name|f
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browse
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|settings
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
comment|/**      * This method is called to indicate that the settings panel returned from the getSettingsPanel() method has been      * shown to the user and that the user has indicated that the settings should be stored. This method must store the      * state of the widgets in the settings panel to Globals.prefs.      */
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|application
operator|.
name|commandPathPreferenceKey
argument_list|,
name|path
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

