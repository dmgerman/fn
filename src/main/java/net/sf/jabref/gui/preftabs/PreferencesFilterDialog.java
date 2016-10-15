begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.preftabs
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
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
name|awt
operator|.
name|FlowLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
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
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|AbstractTableModel
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
name|gui
operator|.
name|WrapLayout
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
name|gui
operator|.
name|util
operator|.
name|GUIUtil
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|preferences
operator|.
name|JabRefPreferencesFilter
import|;
end_import

begin_class
DECL|class|PreferencesFilterDialog
class|class
name|PreferencesFilterDialog
extends|extends
name|JDialog
block|{
DECL|field|preferencesFilter
specifier|private
specifier|final
name|JabRefPreferencesFilter
name|preferencesFilter
decl_stmt|;
DECL|field|table
specifier|private
specifier|final
name|JTable
name|table
decl_stmt|;
DECL|field|showOnlyDeviatingPreferenceOptions
specifier|private
specifier|final
name|JCheckBox
name|showOnlyDeviatingPreferenceOptions
decl_stmt|;
DECL|field|count
specifier|private
specifier|final
name|JLabel
name|count
decl_stmt|;
DECL|method|PreferencesFilterDialog (JabRefPreferencesFilter preferencesFilter, JFrame frame)
specifier|public
name|PreferencesFilterDialog
parameter_list|(
name|JabRefPreferencesFilter
name|preferencesFilter
parameter_list|,
name|JFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// is modal
name|this
operator|.
name|preferencesFilter
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferencesFilter
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preferences"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|800
argument_list|,
literal|600
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|panel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|northPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|northPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|WrapLayout
argument_list|(
name|FlowLayout
operator|.
name|LEFT
argument_list|)
argument_list|)
expr_stmt|;
name|showOnlyDeviatingPreferenceOptions
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show only preferences deviating from their default value"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|showOnlyDeviatingPreferenceOptions
operator|.
name|addChangeListener
argument_list|(
name|x
lambda|->
name|updateModel
argument_list|()
argument_list|)
expr_stmt|;
name|northPanel
operator|.
name|add
argument_list|(
name|showOnlyDeviatingPreferenceOptions
argument_list|)
expr_stmt|;
name|count
operator|=
operator|new
name|JLabel
argument_list|()
expr_stmt|;
name|northPanel
operator|.
name|add
argument_list|(
name|count
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|northPanel
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|table
operator|=
operator|new
name|JTable
argument_list|()
expr_stmt|;
name|table
operator|.
name|setAutoCreateRowSorter
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|GUIUtil
operator|.
name|correctRowHeight
argument_list|(
name|table
argument_list|)
expr_stmt|;
name|updateModel
argument_list|()
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|this
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
DECL|method|updateModel ()
specifier|private
name|void
name|updateModel
parameter_list|()
block|{
name|List
argument_list|<
name|JabRefPreferencesFilter
operator|.
name|PreferenceOption
argument_list|>
name|preferenceOptions
decl_stmt|;
if|if
condition|(
name|showOnlyDeviatingPreferenceOptions
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|preferenceOptions
operator|=
name|preferencesFilter
operator|.
name|getDeviatingPreferences
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|preferenceOptions
operator|=
name|preferencesFilter
operator|.
name|getPreferenceOptions
argument_list|()
expr_stmt|;
block|}
name|table
operator|.
name|setModel
argument_list|(
operator|new
name|PreferencesTableModel
argument_list|(
name|preferenceOptions
argument_list|)
argument_list|)
expr_stmt|;
name|count
operator|.
name|setText
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"(%d)"
argument_list|,
name|preferenceOptions
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|PreferencesTableModel
specifier|private
specifier|static
class|class
name|PreferencesTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|preferences
specifier|private
specifier|final
name|List
argument_list|<
name|JabRefPreferencesFilter
operator|.
name|PreferenceOption
argument_list|>
name|preferences
decl_stmt|;
DECL|method|PreferencesTableModel (List<JabRefPreferencesFilter.PreferenceOption> preferences)
specifier|public
name|PreferencesTableModel
parameter_list|(
name|List
argument_list|<
name|JabRefPreferencesFilter
operator|.
name|PreferenceOption
argument_list|>
name|preferences
parameter_list|)
block|{
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
block|}
annotation|@
name|Override
DECL|method|getColumnName (int column)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|==
literal|0
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"type"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|column
operator|==
literal|1
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"key"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|column
operator|==
literal|2
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"value"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|column
operator|==
literal|3
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"default"
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|"n/a"
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|preferences
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|4
return|;
block|}
annotation|@
name|Override
DECL|method|getValueAt (int rowIndex, int columnIndex)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|)
block|{
if|if
condition|(
operator|(
name|rowIndex
operator|<
literal|0
operator|)
operator|||
operator|(
operator|(
name|rowIndex
operator|-
literal|1
operator|)
operator|>
name|preferences
operator|.
name|size
argument_list|()
operator|)
condition|)
block|{
return|return
literal|"n/a"
return|;
block|}
name|JabRefPreferencesFilter
operator|.
name|PreferenceOption
name|preferenceOption
init|=
name|preferences
operator|.
name|get
argument_list|(
name|rowIndex
argument_list|)
decl_stmt|;
if|if
condition|(
name|columnIndex
operator|==
literal|0
condition|)
block|{
return|return
name|preferenceOption
operator|.
name|getType
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|1
condition|)
block|{
return|return
name|preferenceOption
operator|.
name|getKey
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|2
condition|)
block|{
return|return
name|preferenceOption
operator|.
name|getValue
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|3
condition|)
block|{
return|return
name|preferenceOption
operator|.
name|getDefaultValue
argument_list|()
operator|.
name|orElse
argument_list|(
literal|"NULL"
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|"n/a"
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

