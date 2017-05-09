begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
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
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|GUIGlobals
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
name|autocompleter
operator|.
name|AutoCompleteListener
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
comment|/**  * An implementation of the FieldEditor backed by a {@link EditorTextArea}.  * Used for multi-line input, currently all BibTexFields except Bibtex key!  */
end_comment

begin_class
DECL|class|TextArea
specifier|public
class|class
name|TextArea
implements|implements
name|FieldEditor
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
name|TextArea
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|swingPanel
specifier|private
specifier|final
name|JFXPanel
name|swingPanel
decl_stmt|;
DECL|field|textArea
specifier|private
specifier|final
name|EditorTextArea
name|textArea
decl_stmt|;
DECL|field|fieldName
specifier|private
name|String
name|fieldName
decl_stmt|;
DECL|field|autoCompleteListener
specifier|private
name|AutoCompleteListener
name|autoCompleteListener
decl_stmt|;
DECL|method|TextArea (String fieldName, String content)
specifier|public
name|TextArea
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|this
argument_list|(
name|fieldName
argument_list|,
name|content
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|TextArea (String fieldName, String content, String title)
specifier|public
name|TextArea
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|content
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|textArea
operator|=
operator|new
name|EditorTextArea
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|textArea
operator|.
name|setPromptText
argument_list|(
name|title
argument_list|)
expr_stmt|;
name|swingPanel
operator|=
operator|new
name|JFXPanel
argument_list|()
expr_stmt|;
name|swingPanel
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|activeBackgroundColor
argument_list|)
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|Scene
name|scene
init|=
operator|new
name|Scene
argument_list|(
name|textArea
argument_list|)
decl_stmt|;
name|swingPanel
operator|.
name|setScene
argument_list|(
name|scene
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
comment|/*         // Add the global focus listener, so a menu item can see if this field         // was focused when an action was called.         addFocusListener(Globals.getFocusListener());         addFocusListener(new FieldEditorFocusListener());         */
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
comment|/*         FieldTextMenu popMenu = new FieldTextMenu(this);         this.addMouseListener(popMenu);         label.addMouseListener(popMenu);         */
block|}
annotation|@
name|Override
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
DECL|method|setFieldName (String newName)
specifier|public
name|void
name|setFieldName
parameter_list|(
name|String
name|newName
parameter_list|)
block|{
name|fieldName
operator|=
name|newName
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setBackground (Color color)
specifier|public
name|void
name|setBackground
parameter_list|(
name|Color
name|color
parameter_list|)
block|{      }
annotation|@
name|Override
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|swingPanel
return|;
block|}
annotation|@
name|Override
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|hasFocus ()
specifier|public
name|boolean
name|hasFocus
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|setActiveBackgroundColor ()
specifier|public
name|void
name|setActiveBackgroundColor
parameter_list|()
block|{
name|setBackgroundColor
argument_list|(
name|GUIGlobals
operator|.
name|activeBackgroundColor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValidBackgroundColor ()
specifier|public
name|void
name|setValidBackgroundColor
parameter_list|()
block|{
name|setBackgroundColor
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setInvalidBackgroundColor ()
specifier|public
name|void
name|setInvalidBackgroundColor
parameter_list|()
block|{
name|setBackgroundColor
argument_list|(
name|GUIGlobals
operator|.
name|invalidFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
DECL|method|setBackgroundColor (Color color)
specifier|private
name|void
name|setBackgroundColor
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
if|if
condition|(
name|SwingUtilities
operator|.
name|isEventDispatchThread
argument_list|()
condition|)
block|{
name|setBackground
argument_list|(
name|color
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|SwingUtilities
operator|.
name|invokeAndWait
argument_list|(
parameter_list|()
lambda|->
name|setBackground
argument_list|(
name|color
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
decl||
name|InterruptedException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Problem setting background color"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|textArea
operator|.
name|getText
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|setText (String newText)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|newText
parameter_list|)
block|{
name|textArea
operator|.
name|setText
argument_list|(
name|newText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|append (String text)
specifier|public
name|void
name|append
parameter_list|(
name|String
name|text
parameter_list|)
block|{      }
annotation|@
name|Override
DECL|method|setEnabled (boolean enabled)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{      }
annotation|@
name|Override
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
block|{
comment|/*         replaceSelection(textToInsert);         */
block|}
annotation|@
name|Override
DECL|method|getSelectedText ()
specifier|public
name|String
name|getSelectedText
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
comment|// Nothing
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
comment|// Nothing
block|}
annotation|@
name|Override
DECL|method|setAutoCompleteListener (AutoCompleteListener listener)
specifier|public
name|void
name|setAutoCompleteListener
parameter_list|(
name|AutoCompleteListener
name|listener
parameter_list|)
block|{
name|autoCompleteListener
operator|=
name|listener
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|clearAutoCompleteSuggestion ()
specifier|public
name|void
name|clearAutoCompleteSuggestion
parameter_list|()
block|{
comment|/*         if (autoCompleteListener != null) {             autoCompleteListener.clearCurrentSuggestion(this);         }         */
block|}
annotation|@
name|Override
DECL|method|requestFocus ()
specifier|public
name|void
name|requestFocus
parameter_list|()
block|{      }
block|}
end_class

end_unit

