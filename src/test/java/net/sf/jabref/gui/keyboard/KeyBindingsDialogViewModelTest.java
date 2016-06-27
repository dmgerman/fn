begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TreeItem
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
name|KeyCode
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
name|KeyCombination
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNull
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_comment
comment|/**  * Test class for the keybindings dialog view model  *  */
end_comment

begin_class
DECL|class|KeyBindingsDialogViewModelTest
specifier|public
class|class
name|KeyBindingsDialogViewModelTest
block|{
DECL|field|model
name|KeyBindingsDialogViewModel
name|model
init|=
literal|null
decl_stmt|;
DECL|field|keyBindingsPreferences
name|KeyBindingPreferences
name|keyBindingsPreferences
init|=
literal|null
decl_stmt|;
DECL|field|keyBindingRepository
name|KeyBindingRepository
name|keyBindingRepository
init|=
literal|null
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|model
operator|=
operator|new
name|KeyBindingsDialogViewModel
argument_list|()
expr_stmt|;
name|JabRefPreferences
name|mockedPreferences
init|=
name|mock
argument_list|(
name|JabRefPreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|keyBindingsPreferences
operator|=
operator|new
name|KeyBindingPreferences
argument_list|(
name|mockedPreferences
argument_list|)
expr_stmt|;
name|model
operator|.
name|setKeyBindingPreferences
argument_list|(
name|keyBindingsPreferences
argument_list|)
expr_stmt|;
name|keyBindingRepository
operator|=
name|model
operator|.
name|getKeyBindingRepository
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testInvalidKeyBindingIsNotSaved ()
specifier|public
name|void
name|testInvalidKeyBindingIsNotSaved
parameter_list|()
block|{
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|COPY
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_RELEASED
argument_list|,
literal|"Q"
argument_list|,
literal|"Q"
argument_list|,
name|KeyCode
operator|.
name|Q
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|COPY
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|KeyCombination
name|combination
init|=
name|KeyCombination
operator|.
name|keyCombination
argument_list|(
name|keyBindingRepository
operator|.
name|get
argument_list|(
name|KeyBinding
operator|.
name|COPY
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|combination
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|COPY
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSpecialKeysValidKeyBindingIsSaved ()
specifier|public
name|void
name|testSpecialKeysValidKeyBindingIsSaved
parameter_list|()
block|{
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_NEW_DATABASE
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_RELEASED
argument_list|,
literal|"F1"
argument_list|,
literal|"F1"
argument_list|,
name|KeyCode
operator|.
name|F1
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_NEW_DATABASE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|KeyCombination
name|combination
init|=
name|KeyCombination
operator|.
name|keyCombination
argument_list|(
name|keyBindingRepository
operator|.
name|get
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_NEW_DATABASE
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|combination
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_NEW_DATABASE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testKeyBindingCategory ()
specifier|public
name|void
name|testKeyBindingCategory
parameter_list|()
block|{
name|KeyBindingViewModel
name|bindViewModel
init|=
operator|new
name|KeyBindingViewModel
argument_list|(
name|KeyBindingCategory
operator|.
name|FILE
argument_list|)
decl_stmt|;
name|model
operator|.
name|getSelectedKeyBinding
argument_list|()
operator|.
name|set
argument_list|(
operator|new
name|TreeItem
argument_list|<>
argument_list|(
name|bindViewModel
argument_list|)
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|"M"
argument_list|,
literal|"M"
argument_list|,
name|KeyCode
operator|.
name|M
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|CLEANUP
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|model
operator|.
name|getSelectedKeyBinding
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|getKeyBinding
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRandomNewKeyKeyBindingInRepository ()
specifier|public
name|void
name|testRandomNewKeyKeyBindingInRepository
parameter_list|()
block|{
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|CLEANUP
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|"K"
argument_list|,
literal|"K"
argument_list|,
name|KeyCode
operator|.
name|K
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|CLEANUP
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|KeyCombination
name|combination
init|=
name|KeyCombination
operator|.
name|keyCombination
argument_list|(
name|keyBindingRepository
operator|.
name|get
argument_list|(
name|KeyBinding
operator|.
name|CLEANUP
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|combination
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|CLEANUP
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSaveNewKeyBindingsToPreferences ()
specifier|public
name|void
name|testSaveNewKeyBindingsToPreferences
parameter_list|()
block|{
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|"J"
argument_list|,
literal|"J"
argument_list|,
name|KeyCode
operator|.
name|J
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSaveNewSpecialKeysKeyBindingsToPreferences ()
specifier|public
name|void
name|testSaveNewSpecialKeysKeyBindingsToPreferences
parameter_list|()
block|{
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|UNMARK_ENTRIES
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|"F1"
argument_list|,
literal|"F1"
argument_list|,
name|KeyCode
operator|.
name|F1
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|UNMARK_ENTRIES
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|UNMARK_ENTRIES
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetAllKeyBindingsToDefault ()
specifier|public
name|void
name|testSetAllKeyBindingsToDefault
parameter_list|()
block|{
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|"C"
argument_list|,
literal|"C"
argument_list|,
name|KeyCode
operator|.
name|C
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|resetKeyBindingsToDefault
argument_list|()
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetSingleKeyBindingToDefault ()
specifier|public
name|void
name|testSetSingleKeyBindingToDefault
parameter_list|()
block|{
name|KeyBindingViewModel
name|viewModel
init|=
name|setKeyBindingViewModel
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|)
decl_stmt|;
name|model
operator|.
name|getSelectedKeyBinding
argument_list|()
operator|.
name|set
argument_list|(
operator|new
name|TreeItem
argument_list|<>
argument_list|(
name|viewModel
argument_list|)
argument_list|)
expr_stmt|;
name|KeyEvent
name|shortcutKeyEvent
init|=
operator|new
name|KeyEvent
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
literal|"C"
argument_list|,
literal|"C"
argument_list|,
name|KeyCode
operator|.
name|C
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|grabKey
argument_list|(
name|shortcutKeyEvent
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|resetToDefault
argument_list|(
name|keyBindingRepository
argument_list|)
expr_stmt|;
name|model
operator|.
name|saveKeyBindings
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|keyBindingsPreferences
operator|.
name|checkKeyCombinationEquality
argument_list|(
name|KeyBinding
operator|.
name|ABBREVIATE
argument_list|,
name|shortcutKeyEvent
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setKeyBindingViewModel (KeyBinding binding)
specifier|private
name|KeyBindingViewModel
name|setKeyBindingViewModel
parameter_list|(
name|KeyBinding
name|binding
parameter_list|)
block|{
name|KeyBindingViewModel
name|bindViewModel
init|=
operator|new
name|KeyBindingViewModel
argument_list|(
name|binding
argument_list|,
name|binding
operator|.
name|getDefaultBinding
argument_list|()
argument_list|)
decl_stmt|;
name|model
operator|.
name|getSelectedKeyBinding
argument_list|()
operator|.
name|set
argument_list|(
operator|new
name|TreeItem
argument_list|<>
argument_list|(
name|bindViewModel
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|bindViewModel
return|;
block|}
block|}
end_class

end_unit

