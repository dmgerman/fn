begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|*
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
name|external
operator|.
name|ExternalFilePanel
import|;
end_import

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
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|DefaultFormBuilder
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
name|ButtonBarBuilder
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: May 18, 2005  * Time: 9:59:52 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|AttachFileDialog
specifier|public
class|class
name|AttachFileDialog
extends|extends
name|JDialog
block|{
DECL|field|ths
name|AttachFileDialog
name|ths
init|=
name|this
decl_stmt|;
DECL|field|editor
name|FieldEditor
name|editor
decl_stmt|;
DECL|field|fieldName
name|String
name|fieldName
decl_stmt|;
DECL|field|main
name|JPanel
name|main
decl_stmt|;
DECL|field|browse
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|download
name|download
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|auto
name|auto
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Auto"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|ok
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|entry
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|cancelled
specifier|private
name|boolean
name|cancelled
init|=
literal|true
decl_stmt|;
comment|// Default to true, so a pure close operation implies Cancel.
DECL|method|AttachFileDialog (Frame parent, BibtexEntry entry, String fieldName)
specifier|public
name|AttachFileDialog
parameter_list|(
name|Frame
name|parent
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|editor
operator|=
operator|new
name|FieldTextField
argument_list|(
name|fieldName
argument_list|,
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|initGui
argument_list|()
expr_stmt|;
block|}
DECL|method|AttachFileDialog (Dialog parent, BibtexEntry entry, String fieldName)
specifier|public
name|AttachFileDialog
parameter_list|(
name|Dialog
name|parent
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|editor
operator|=
operator|new
name|FieldTextField
argument_list|(
name|fieldName
argument_list|,
operator|(
name|String
operator|)
name|entry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|initGui
argument_list|()
expr_stmt|;
block|}
DECL|method|cancelled ()
specifier|public
name|boolean
name|cancelled
parameter_list|()
block|{
return|return
name|cancelled
return|;
block|}
DECL|method|getValue ()
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|editor
operator|.
name|getText
argument_list|()
return|;
block|}
DECL|method|initGui ()
specifier|private
name|void
name|initGui
parameter_list|()
block|{
specifier|final
name|ExternalFilePanel
name|extPan
init|=
operator|new
name|ExternalFilePanel
argument_list|(
name|fieldName
argument_list|,
name|entry
argument_list|,
name|Util
operator|.
name|getFileFilterForField
argument_list|(
name|fieldName
argument_list|)
argument_list|)
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|extPan
operator|.
name|browseFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|download
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|extPan
operator|.
name|downLoadFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|,
name|ths
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|auto
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|extPan
operator|.
name|autoSetFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|cancelled
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|cancelled
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"fill:160dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
comment|//builder.append(Util.nCase(fieldName));//(editor.getLabel());
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Util
operator|.
name|nCase
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|editor
operator|.
name|getTextComponent
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browse
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|download
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|auto
argument_list|)
expr_stmt|;
comment|//builder.append(Globals.lang("Advanced options for setting..."));
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|()
expr_stmt|;
name|main
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|bb
operator|=
operator|new
name|ButtonBarBuilder
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

