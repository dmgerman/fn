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
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexFields
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
name|GUIGlobals
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
name|Globals
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
name|JabRefFrame
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Aug 23, 2005  * Time: 11:30:48 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|FieldWeightDialog
specifier|public
class|class
name|FieldWeightDialog
extends|extends
name|JDialog
block|{
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|sliders
name|HashMap
argument_list|<
name|JSlider
argument_list|,
name|SliderInfo
argument_list|>
name|sliders
init|=
operator|new
name|HashMap
argument_list|<
name|JSlider
argument_list|,
name|SliderInfo
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|ok
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"OK"
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
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
operator|new
name|FieldWeightDialog
argument_list|(
literal|null
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldWeightDialog (JabRefFrame frame)
specifier|public
name|FieldWeightDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|JPanel
name|main
init|=
name|buildMainPanel
argument_list|()
decl_stmt|;
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
name|buildButtonPanel
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
DECL|method|buildMainPanel ()
specifier|public
name|JPanel
name|buildMainPanel
parameter_list|()
block|{
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"right:pref, 4dlu, fill:pref, 8dlu, right:pref, 4dlu, fill:pref"
argument_list|,
comment|// 4dlu, left:pref, 4dlu",
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field sizes"
argument_list|)
argument_list|)
expr_stmt|;
comment|// We use this list to build an alphabetical list of field names:
name|TreeSet
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|// We use this map to remember which slider represents which field name:
name|sliders
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|len
init|=
name|BibtexFields
operator|.
name|numberOfPublicFields
argument_list|()
init|;
name|i
operator|<
name|len
condition|;
name|i
operator|++
control|)
block|{
name|fields
operator|.
name|add
argument_list|(
name|BibtexFields
operator|.
name|getFieldName
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|fields
operator|.
name|remove
argument_list|(
literal|"bibtexkey"
argument_list|)
expr_stmt|;
comment|// bibtex key doesn't need weight.
comment|// Here is the place to add other fields:
comment|// --------------
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|fields
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|field
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|int
name|weight
init|=
call|(
name|int
call|)
argument_list|(
literal|100
operator|*
name|BibtexFields
operator|.
name|getFieldWeight
argument_list|(
name|field
argument_list|)
operator|/
name|GUIGlobals
operator|.
name|MAX_FIELD_WEIGHT
argument_list|)
decl_stmt|;
comment|//System.out.println(weight);
name|JSlider
name|slider
init|=
operator|new
name|JSlider
argument_list|(
literal|0
argument_list|,
literal|100
argument_list|,
name|weight
argument_list|)
decl_stmt|;
comment|//,);
name|sliders
operator|.
name|put
argument_list|(
name|slider
argument_list|,
operator|new
name|SliderInfo
argument_list|(
name|field
argument_list|,
name|weight
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|slider
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|appendSeparator
argument_list|()
expr_stmt|;
return|return
name|builder
operator|.
name|getPanel
argument_list|()
return|;
block|}
DECL|method|buildButtonPanel ()
specifier|public
name|JPanel
name|buildButtonPanel
parameter_list|()
block|{
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
name|actionEvent
parameter_list|)
block|{
name|storeSettings
argument_list|()
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
name|actionEvent
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|builder
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|builder
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|builder
operator|.
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addGridded
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addGlue
argument_list|()
expr_stmt|;
return|return
name|builder
operator|.
name|getPanel
argument_list|()
return|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
for|for
control|(
name|Iterator
argument_list|<
name|JSlider
argument_list|>
name|i
init|=
name|sliders
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|JSlider
name|slider
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|SliderInfo
name|sInfo
init|=
name|sliders
operator|.
name|get
argument_list|(
name|slider
argument_list|)
decl_stmt|;
comment|// Only list the value if it has changed:
if|if
condition|(
name|sInfo
operator|.
name|originalValue
operator|!=
name|slider
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|double
name|weight
init|=
name|GUIGlobals
operator|.
name|MAX_FIELD_WEIGHT
operator|*
operator|(
operator|(
name|double
operator|)
name|slider
operator|.
name|getValue
argument_list|()
operator|)
operator|/
literal|100d
decl_stmt|;
name|BibtexFields
operator|.
name|setFieldWeight
argument_list|(
name|sInfo
operator|.
name|fieldName
argument_list|,
name|weight
argument_list|)
expr_stmt|;
block|}
block|}
name|frame
operator|.
name|removeCachedEntryEditors
argument_list|()
expr_stmt|;
block|}
comment|/**      * "Struct" class to hold the necessary info about one of our JSliders:      * which field it represents, and what value it started out with.      */
DECL|class|SliderInfo
specifier|static
class|class
name|SliderInfo
block|{
DECL|field|fieldName
name|String
name|fieldName
decl_stmt|;
DECL|field|originalValue
name|int
name|originalValue
decl_stmt|;
DECL|method|SliderInfo (String fieldName, int originalValue)
specifier|public
name|SliderInfo
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|int
name|originalValue
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|originalValue
operator|=
name|originalValue
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

