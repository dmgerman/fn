begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.keyboard
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
package|;
end_package

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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Alert
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonBar
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|KeyEvent
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
name|AbstractViewModel
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
name|PreferencesService
import|;
end_import

begin_class
DECL|class|KeyBindingsDialogViewModel
specifier|public
class|class
name|KeyBindingsDialogViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|keyBindingRepository
specifier|private
specifier|final
name|KeyBindingRepository
name|keyBindingRepository
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|selectedKeyBinding
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|KeyBindingViewModel
argument_list|>
name|selectedKeyBinding
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|rootKeyBinding
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|KeyBindingViewModel
argument_list|>
name|rootKeyBinding
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|KeyBindingsDialogViewModel (KeyBindingRepository keyBindingRepository, DialogService dialogService, PreferencesService preferences)
specifier|public
name|KeyBindingsDialogViewModel
parameter_list|(
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|PreferencesService
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|keyBindingRepository
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|keyBindingRepository
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|populateTable
argument_list|()
expr_stmt|;
block|}
DECL|method|selectedKeyBindingProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|KeyBindingViewModel
argument_list|>
name|selectedKeyBindingProperty
parameter_list|()
block|{
return|return
name|selectedKeyBinding
return|;
block|}
DECL|method|rootKeyBindingProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|KeyBindingViewModel
argument_list|>
name|rootKeyBindingProperty
parameter_list|()
block|{
return|return
name|rootKeyBinding
return|;
block|}
comment|/**      * Read all keybindings from the keybinding repository and create table keybinding models for them      */
DECL|method|populateTable ()
specifier|private
name|void
name|populateTable
parameter_list|()
block|{
name|KeyBindingViewModel
name|root
init|=
operator|new
name|KeyBindingViewModel
argument_list|(
name|keyBindingRepository
argument_list|,
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
decl_stmt|;
for|for
control|(
name|KeyBindingCategory
name|category
range|:
name|KeyBindingCategory
operator|.
name|values
argument_list|()
control|)
block|{
name|KeyBindingViewModel
name|categoryItem
init|=
operator|new
name|KeyBindingViewModel
argument_list|(
name|keyBindingRepository
argument_list|,
name|category
argument_list|)
decl_stmt|;
name|keyBindingRepository
operator|.
name|getKeyBindings
argument_list|()
operator|.
name|forEach
argument_list|(
parameter_list|(
name|keyBinding
parameter_list|,
name|bind
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|keyBinding
operator|.
name|getCategory
argument_list|()
operator|==
name|category
condition|)
block|{
name|KeyBindingViewModel
name|keyBindViewModel
init|=
operator|new
name|KeyBindingViewModel
argument_list|(
name|keyBindingRepository
argument_list|,
name|keyBinding
argument_list|,
name|bind
argument_list|)
decl_stmt|;
name|categoryItem
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|keyBindViewModel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|root
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|categoryItem
argument_list|)
expr_stmt|;
block|}
name|rootKeyBinding
operator|.
name|set
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
DECL|method|setNewBindingForCurrent (KeyEvent event)
specifier|public
name|void
name|setNewBindingForCurrent
parameter_list|(
name|KeyEvent
name|event
parameter_list|)
block|{
comment|// first check if a valid entry is selected
if|if
condition|(
name|selectedKeyBinding
operator|.
name|isNull
argument_list|()
operator|.
name|get
argument_list|()
condition|)
block|{
return|return;
block|}
name|KeyBindingViewModel
name|selectedEntry
init|=
name|selectedKeyBinding
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|selectedEntry
operator|==
literal|null
operator|)
operator|||
operator|(
name|selectedEntry
operator|.
name|isCategory
argument_list|()
operator|)
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|selectedEntry
operator|.
name|setNewBinding
argument_list|(
name|event
argument_list|)
condition|)
block|{
name|keyBindingRepository
operator|.
name|put
argument_list|(
name|selectedEntry
operator|.
name|getKeyBinding
argument_list|()
argument_list|,
name|selectedEntry
operator|.
name|getBinding
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|saveKeyBindings ()
specifier|public
name|void
name|saveKeyBindings
parameter_list|()
block|{
name|preferences
operator|.
name|storeKeyBindingRepository
argument_list|(
name|keyBindingRepository
argument_list|)
expr_stmt|;
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Key bindings changed"
argument_list|)
decl_stmt|;
name|String
name|content
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Your new key bindings have been stored."
argument_list|)
operator|+
literal|'\n'
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for the new key bindings to work properly."
argument_list|)
decl_stmt|;
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|title
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
DECL|method|resetToDefault ()
specifier|public
name|void
name|resetToDefault
parameter_list|()
block|{
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resetting all key bindings"
argument_list|)
decl_stmt|;
name|String
name|content
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"All key bindings will be reset to their defaults."
argument_list|)
decl_stmt|;
name|ButtonType
name|resetButtonType
init|=
operator|new
name|ButtonType
argument_list|(
literal|"Reset"
argument_list|,
name|ButtonBar
operator|.
name|ButtonData
operator|.
name|OK_DONE
argument_list|)
decl_stmt|;
name|dialogService
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|Alert
operator|.
name|AlertType
operator|.
name|INFORMATION
argument_list|,
name|title
argument_list|,
name|content
argument_list|,
name|resetButtonType
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|response
lambda|->
block|{
if|if
condition|(
name|response
operator|==
name|resetButtonType
condition|)
block|{
name|keyBindingRepository
operator|.
name|resetToDefault
argument_list|()
expr_stmt|;
name|populateTable
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

