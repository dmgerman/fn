begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ButtonGroup
import|;
end_import

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
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComboBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JSpinner
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
name|javax
operator|.
name|swing
operator|.
name|SpinnerNumberModel
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|help
operator|.
name|HelpAction
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
comment|/**  * Preferences tab for file options. These options were moved out from GeneralTab to  * resolve the space issue.  */
end_comment

begin_class
DECL|class|FileTab
specifier|public
class|class
name|FileTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|backup
DECL|field|openLast
DECL|field|autoDoubleBraces
DECL|field|autoSave
specifier|private
name|JCheckBox
name|backup
decl_stmt|,
name|openLast
decl_stmt|,
name|autoDoubleBraces
decl_stmt|,
name|autoSave
decl_stmt|,
DECL|field|promptBeforeUsingAutoSave
DECL|field|includeEmptyFields
DECL|field|camelCase
DECL|field|sameColumn
name|promptBeforeUsingAutoSave
decl_stmt|,
name|includeEmptyFields
decl_stmt|,
name|camelCase
decl_stmt|,
name|sameColumn
decl_stmt|;
DECL|field|valueDelimiter
DECL|field|newlineSeparator
specifier|private
name|JComboBox
name|valueDelimiter
decl_stmt|,
name|newlineSeparator
decl_stmt|;
specifier|private
name|JRadioButton
DECL|field|resolveStringsStandard
DECL|field|resolveStringsAll
name|resolveStringsStandard
decl_stmt|,
name|resolveStringsAll
decl_stmt|;
DECL|field|bracesAroundCapitalsFields
DECL|field|nonWrappableFields
specifier|private
name|JTextField
name|bracesAroundCapitalsFields
decl_stmt|,
name|nonWrappableFields
decl_stmt|,
DECL|field|doNotResolveStringsFor
name|doNotResolveStringsFor
decl_stmt|;
DECL|field|autoSaveInterval
specifier|private
name|JSpinner
name|autoSaveInterval
decl_stmt|;
DECL|field|origAutoSaveSetting
specifier|private
name|boolean
name|origAutoSaveSetting
init|=
literal|false
decl_stmt|;
comment|//for LWang_AdjustableFieldOrder
comment|//    private JRadioButton sortFieldInAlphabetaOrder,unSortFieldStyle,orderAsUserDefined;
DECL|field|bgFieldOrderStyle
specifier|private
name|ButtonGroup
name|bgFieldOrderStyle
decl_stmt|;
comment|//    int fieldOrderStyle;
DECL|field|userDefinedFieldOrder
specifier|private
name|JTextField
name|userDefinedFieldOrder
decl_stmt|;
DECL|field|wrapFieldLine
specifier|private
name|JCheckBox
name|wrapFieldLine
decl_stmt|;
DECL|method|FileTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|FileTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|_frame
operator|=
name|frame
expr_stmt|;
name|HelpAction
name|autosaveHelp
init|=
operator|new
name|HelpAction
argument_list|(
name|frame
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|autosaveHelp
argument_list|,
literal|"Help"
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|;
name|openLast
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open last edited databases at startup"
argument_list|)
argument_list|)
expr_stmt|;
name|backup
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Backup old file when saving"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSave
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autosave"
argument_list|)
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Prompt before recovering a database from an autosave file"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSaveInterval
operator|=
operator|new
name|JSpinner
argument_list|(
operator|new
name|SpinnerNumberModel
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|60
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|valueDelimiter
operator|=
operator|new
name|JComboBox
argument_list|(
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Quotes"
argument_list|)
operator|+
literal|": \", \""
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Curly Brackets"
argument_list|)
operator|+
literal|": {, }"
block|}
argument_list|)
expr_stmt|;
name|includeEmptyFields
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Include empty fields"
argument_list|)
argument_list|)
expr_stmt|;
name|sameColumn
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Start field contents in same column"
argument_list|)
argument_list|)
expr_stmt|;
name|camelCase
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use camel case for field names (e.g., \"HowPublished\" instead of \"howpublished\")"
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Resolve strings for all fields except"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|resolveStringsStandard
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Resolve strings for standard BibTeX fields only"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|resolveStringsAll
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|resolveStringsStandard
argument_list|)
expr_stmt|;
comment|//for LWang_AdjustableFieldOrder
comment|//        ButtonGroup bgFieldOrderStyle=new ButtonGroup();
comment|//        sortFieldInAlphabetaOrder = new JRadioButton(Globals.lang("Sort fields in alphabeta order (as ver>= 2.10)"));
comment|//        unSortFieldStyle = new JRadioButton(Globals.lang("Do not sort fields (as ver<=2.9.2)"));
comment|//        orderAsUserDefined= new JRadioButton(Globals.lang("Save fields as user defined order"));
comment|//        bgFieldOrderStyle.add(sortFieldInAlphabetaOrder);
comment|//        bgFieldOrderStyle.add(unSortFieldStyle);
comment|//        bgFieldOrderStyle.add(orderAsUserDefined);
name|userDefinedFieldOrder
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_USERDEFINEDORDER
argument_list|)
argument_list|)
expr_stmt|;
comment|//need to use JcomboBox in the future
comment|// This is sort of a quick hack
name|newlineSeparator
operator|=
operator|new
name|JComboBox
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"CR"
block|,
literal|"CR/LF"
block|,
literal|"LF"
block|}
argument_list|)
expr_stmt|;
name|bracesAroundCapitalsFields
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|nonWrappableFields
operator|=
operator|new
name|JTextField
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|autoDoubleBraces
operator|=
operator|new
name|JCheckBox
argument_list|(
comment|//+ Globals.lang("Store fields with double braces, and remove extra braces when loading.<BR>"
comment|//+ "Double braces signal that BibTeX should preserve character case.") + "</HTML>");
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove double braces around BibTeX fields when loading."
argument_list|)
argument_list|)
expr_stmt|;
name|autoSave
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|changeEvent
parameter_list|)
block|{
name|autoSaveInterval
operator|.
name|setEnabled
argument_list|(
name|autoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|.
name|setEnabled
argument_list|(
name|autoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
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
literal|"left:pref, 4dlu, fill:pref"
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"General"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|openLast
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|backup
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoDoubleBraces
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Store the following fields with braces around capital letters"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|bracesAroundCapitalsFields
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Do not wrap the following fields when saving"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|nonWrappableFields
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|resolveStringsStandard
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|resolveStringsAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|doNotResolveStringsFor
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Newline separator"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|newlineSeparator
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
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autosave"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoSave
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|JButton
name|hlp
init|=
operator|new
name|JButton
argument_list|(
name|autosaveHelp
argument_list|)
decl_stmt|;
name|hlp
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hlp
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|24
argument_list|,
literal|24
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|hPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|hPan
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|hPan
operator|.
name|add
argument_list|(
name|hlp
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|hPan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autosave interval (minutes)"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoSaveInterval
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|promptBeforeUsingAutoSave
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
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field saving options"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|camelCase
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|sameColumn
argument_list|)
expr_stmt|;
comment|/*FormLayout layout2 = new FormLayout(                 "left:pref, 8dlu, fill:pref", "");         DefaultFormBuilder builder2 = new DefaultFormBuilder(layout2);     	builder2.append(new JLabel(Globals.lang("Field value delimiter. E.g., \"author={x}\" or \"author='x'\"") + ":"));         builder2.append(valueDelimiter);         builder.nextLine();         builder.append(builder2.getPanel());*/
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
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
name|append
argument_list|(
name|includeEmptyFields
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|wrapFieldLine
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Wrap fields as ver 2.9.2"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|wrapFieldLine
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
comment|//for LWang_AdjustableFieldOrder
name|String
index|[]
name|_rbs0
init|=
block|{
literal|"Sort fields in alphabeta order (as ver 2.10)"
block|,
literal|"Sort fields in old fasion (as ver 2.9.2)"
block|,
literal|"Save fields as user defined order"
block|}
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|_rbs
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|_rb
range|:
name|_rbs0
control|)
block|{
name|_rbs
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
name|_rb
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|bgFieldOrderStyle
operator|=
name|createRadioBg
argument_list|(
name|_rbs
argument_list|)
expr_stmt|;
name|userDefinedFieldOrder
operator|=
operator|new
name|JTextField
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_USERDEFINEDORDER
argument_list|)
argument_list|)
expr_stmt|;
comment|//need to use JcomboBox in the future
name|createAdFieldOrderBg
argument_list|(
name|builder
argument_list|,
name|bgFieldOrderStyle
argument_list|,
name|userDefinedFieldOrder
argument_list|)
expr_stmt|;
comment|//        builder.append(sortFieldInAlphabetaOrder);
comment|//        builder.nextLine();
comment|//        builder.append(unSortFieldStyle);
comment|//        builder.nextLine();
comment|//        builder.append(orderAsUserDefined);
comment|//        builder.append(userDefinedFieldOrder);
comment|//        builder.nextLine();
name|JPanel
name|pan
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|pan
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
DECL|method|createRadioBg (Iterable<String> radioButtonLabels)
specifier|private
name|ButtonGroup
name|createRadioBg
parameter_list|(
name|Iterable
argument_list|<
name|String
argument_list|>
name|radioButtonLabels
parameter_list|)
block|{
name|ButtonGroup
name|_bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|_s
range|:
name|radioButtonLabels
control|)
block|{
name|JRadioButton
name|_rb
init|=
operator|new
name|JRadioButton
argument_list|(
name|_s
argument_list|)
decl_stmt|;
name|_bg
operator|.
name|add
argument_list|(
name|_rb
argument_list|)
expr_stmt|;
block|}
return|return
name|_bg
return|;
block|}
DECL|method|getBgValue (ButtonGroup bg)
specifier|private
name|int
name|getBgValue
parameter_list|(
name|ButtonGroup
name|bg
parameter_list|)
block|{
name|int
name|_i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|AbstractButton
argument_list|>
name|_it
init|=
name|bg
operator|.
name|getElements
argument_list|()
init|;
name|_it
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
if|if
condition|(
name|_it
operator|.
name|nextElement
argument_list|()
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|_i
return|;
block|}
name|_i
operator|++
expr_stmt|;
block|}
return|return
literal|0
return|;
block|}
DECL|method|setBgSelected (ButtonGroup bg,int x)
specifier|private
name|void
name|setBgSelected
parameter_list|(
name|ButtonGroup
name|bg
parameter_list|,
name|int
name|x
parameter_list|)
block|{
name|int
name|_i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|AbstractButton
argument_list|>
name|_it
init|=
name|bg
operator|.
name|getElements
argument_list|()
init|;
name|_it
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
if|if
condition|(
name|_i
operator|==
name|x
condition|)
block|{
name|_it
operator|.
name|nextElement
argument_list|()
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|_it
operator|.
name|nextElement
argument_list|()
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|_i
operator|++
expr_stmt|;
block|}
block|}
comment|//    private void setValueFieldOrderStyle(){
comment|//        fieldOrderStyle=getBgValue(bgFieldOrderStyle);
comment|//    }
DECL|method|createAdFieldOrderBg (DefaultFormBuilder builder, ButtonGroup bg, JTextField jtf)
specifier|private
name|void
name|createAdFieldOrderBg
parameter_list|(
name|DefaultFormBuilder
name|builder
parameter_list|,
name|ButtonGroup
name|bg
parameter_list|,
name|JTextField
name|jtf
parameter_list|)
block|{
comment|//for LWang_AdjustableFieldOrder
for|for
control|(
name|Enumeration
argument_list|<
name|AbstractButton
argument_list|>
name|_it
init|=
name|bg
operator|.
name|getElements
argument_list|()
init|;
name|_it
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|_it
operator|.
name|nextElement
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
name|builder
operator|.
name|append
argument_list|(
name|jtf
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|openLast
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"openLastEdited"
argument_list|)
argument_list|)
expr_stmt|;
name|backup
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"backup"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|newline
init|=
name|_prefs
operator|.
name|get
argument_list|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
operator|.
name|NEWLINE
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"\r"
operator|.
name|equals
argument_list|(
name|newline
argument_list|)
condition|)
block|{
name|newlineSeparator
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"\n"
operator|.
name|equals
argument_list|(
name|newline
argument_list|)
condition|)
block|{
name|newlineSeparator
operator|.
name|setSelectedIndex
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// fallback: windows standard
name|newlineSeparator
operator|.
name|setSelectedIndex
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
comment|//preserveFormatting.setSelected(_prefs.getBoolean("preserveFieldFormatting"));
name|wrapFieldLine
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_WRAPFIELD
argument_list|)
argument_list|)
expr_stmt|;
name|autoDoubleBraces
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autoDoubleBraces"
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsAll
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"resolveStringsAllFields"
argument_list|)
argument_list|)
expr_stmt|;
name|resolveStringsStandard
operator|.
name|setSelected
argument_list|(
operator|!
name|resolveStringsAll
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"doNotResolveStringsFor"
argument_list|)
argument_list|)
expr_stmt|;
name|bracesAroundCapitalsFields
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"putBracesAroundCapitals"
argument_list|)
argument_list|)
expr_stmt|;
name|nonWrappableFields
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"nonWrappableFields"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSave
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"autoSave"
argument_list|)
argument_list|)
expr_stmt|;
name|promptBeforeUsingAutoSave
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"promptBeforeUsingAutosave"
argument_list|)
argument_list|)
expr_stmt|;
name|autoSaveInterval
operator|.
name|setValue
argument_list|(
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"autoSaveInterval"
argument_list|)
argument_list|)
expr_stmt|;
name|origAutoSaveSetting
operator|=
name|autoSave
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|valueDelimiter
operator|.
name|setSelectedIndex
argument_list|(
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"valueDelimiters"
argument_list|)
argument_list|)
expr_stmt|;
name|includeEmptyFields
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"includeEmptyFields"
argument_list|)
argument_list|)
expr_stmt|;
name|camelCase
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_CAMELCASENAME
argument_list|)
argument_list|)
expr_stmt|;
name|sameColumn
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_ADDSPACES
argument_list|)
argument_list|)
expr_stmt|;
comment|//for LWang_AdjustableFieldOrder
name|setBgSelected
argument_list|(
name|bgFieldOrderStyle
argument_list|,
name|_prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_SORTSTYLE
argument_list|)
argument_list|)
expr_stmt|;
name|userDefinedFieldOrder
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_USERDEFINEDORDER
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|String
name|newline
decl_stmt|;
switch|switch
condition|(
name|newlineSeparator
operator|.
name|getSelectedIndex
argument_list|()
condition|)
block|{
case|case
literal|0
case|:
name|newline
operator|=
literal|"\r"
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|newline
operator|=
literal|"\n"
expr_stmt|;
break|break;
default|default:
name|newline
operator|=
literal|"\r\n"
expr_stmt|;
block|}
name|_prefs
operator|.
name|put
argument_list|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
operator|.
name|NEWLINE
argument_list|,
name|newline
argument_list|)
expr_stmt|;
comment|// we also have to change Globals variable as globals is not a getter, but a constant
name|Globals
operator|.
name|NEWLINE
operator|=
name|newline
expr_stmt|;
name|Globals
operator|.
name|NEWLINE_LENGTH
operator|=
name|newline
operator|.
name|length
argument_list|()
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"backup"
argument_list|,
name|backup
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"openLastEdited"
argument_list|,
name|openLast
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"autoDoubleBraces"
argument_list|,
name|autoDoubleBraces
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"resolveStringsAllFields"
argument_list|,
name|resolveStringsAll
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"doNotResolveStringsFor"
argument_list|,
name|doNotResolveStringsFor
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"autoSave"
argument_list|,
name|autoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"promptBeforeUsingAutosave"
argument_list|,
name|promptBeforeUsingAutoSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"autoSaveInterval"
argument_list|,
operator|(
name|Integer
operator|)
name|autoSaveInterval
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"valueDelimiters"
argument_list|,
name|valueDelimiter
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"includeEmptyFields"
argument_list|,
name|includeEmptyFields
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_CAMELCASENAME
argument_list|,
name|camelCase
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_ADDSPACES
argument_list|,
name|sameColumn
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|doNotResolveStringsFor
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"doNotResolveStringsFor"
argument_list|)
argument_list|)
expr_stmt|;
comment|//for LWang_AdjustableFieldOrder
name|_prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_SORTSTYLE
argument_list|,
name|getBgValue
argument_list|(
name|bgFieldOrderStyle
argument_list|)
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_USERDEFINEDORDER
argument_list|,
name|userDefinedFieldOrder
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WRITEFIELD_WRAPFIELD
argument_list|,
name|wrapFieldLine
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|updateSpecialFields
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|!
name|bracesAroundCapitalsFields
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"putBracesAroundCapitals"
argument_list|)
argument_list|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"putBracesAroundCapitals"
argument_list|,
name|bracesAroundCapitalsFields
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|updateSpecialFields
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|nonWrappableFields
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"nonWrappableFields"
argument_list|)
argument_list|)
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
literal|"nonWrappableFields"
argument_list|,
name|nonWrappableFields
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|updateSpecialFields
operator|=
literal|true
expr_stmt|;
block|}
comment|// If either of the two last entries were changed, run the update for special field handling:
if|if
condition|(
name|updateSpecialFields
condition|)
name|_prefs
operator|.
name|updateSpecialFieldHandling
argument_list|()
expr_stmt|;
comment|// See if we should start or stop the auto save manager:
if|if
condition|(
operator|!
name|origAutoSaveSetting
operator|&&
name|autoSave
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|startAutoSaveManager
argument_list|(
name|_frame
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|origAutoSaveSetting
operator|&&
operator|!
name|autoSave
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|stopAutoSaveManager
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

