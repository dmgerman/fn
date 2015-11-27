begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyChangeEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_comment
comment|/**  * Taken from http://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield  */
end_comment

begin_class
DECL|class|JTextFieldChangeListenerUtil
specifier|public
class|class
name|JTextFieldChangeListenerUtil
block|{
comment|/**      * Installs a listener to receive notification when the text of any      * {@code JTextComponent} is changed. Internally, it installs a      * {@link DocumentListener} on the text component's {@link Document},      * and a {@link PropertyChangeListener} on the text component to detect      * if the {@code Document} itself is replaced.      *      * Taken from      *      * @param text any text component, such as a {@link JTextField}      *        or {@link JTextArea}      * @param changeListener a listener to receieve {@link ChangeEvent}s      *        when the text is changed; the source object for the events      *        will be the text component      * @throws NullPointerException if either parameter is null      */
DECL|method|addChangeListener (JTextComponent text, ChangeListener changeListener)
specifier|public
specifier|static
name|void
name|addChangeListener
parameter_list|(
name|JTextComponent
name|text
parameter_list|,
name|ChangeListener
name|changeListener
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|changeListener
argument_list|)
expr_stmt|;
name|DocumentListener
name|dl
init|=
operator|new
name|DocumentListener
argument_list|()
block|{
specifier|private
name|int
name|lastChange
init|=
literal|0
decl_stmt|,
name|lastNotifiedChange
init|=
literal|0
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|changedUpdate
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|changedUpdate
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|lastChange
operator|++
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
if|if
condition|(
name|lastNotifiedChange
operator|!=
name|lastChange
condition|)
block|{
name|lastNotifiedChange
operator|=
name|lastChange
expr_stmt|;
name|changeListener
operator|.
name|stateChanged
argument_list|(
operator|new
name|ChangeEvent
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|text
operator|.
name|addPropertyChangeListener
argument_list|(
literal|"document"
argument_list|,
parameter_list|(
name|PropertyChangeEvent
name|e
parameter_list|)
lambda|->
block|{
name|Document
name|d1
init|=
operator|(
name|Document
operator|)
name|e
operator|.
name|getOldValue
argument_list|()
decl_stmt|;
name|Document
name|d2
init|=
operator|(
name|Document
operator|)
name|e
operator|.
name|getNewValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|d1
operator|!=
literal|null
condition|)
name|d1
operator|.
name|removeDocumentListener
argument_list|(
name|dl
argument_list|)
expr_stmt|;
if|if
condition|(
name|d2
operator|!=
literal|null
condition|)
name|d2
operator|.
name|addDocumentListener
argument_list|(
name|dl
argument_list|)
expr_stmt|;
name|dl
operator|.
name|changedUpdate
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Document
name|d
init|=
name|text
operator|.
name|getDocument
argument_list|()
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
name|d
operator|.
name|addDocumentListener
argument_list|(
name|dl
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

