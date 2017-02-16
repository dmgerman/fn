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
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|JComboBox
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|InternalBibtexFields
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|metadata
operator|.
name|SaveOrderConfig
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
DECL|class|SaveOrderConfigDisplay
specifier|public
class|class
name|SaveOrderConfigDisplay
block|{
DECL|field|panel
specifier|private
name|JPanel
name|panel
decl_stmt|;
DECL|field|savePriSort
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|savePriSort
decl_stmt|;
DECL|field|saveSecSort
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|saveSecSort
decl_stmt|;
DECL|field|saveTerSort
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|saveTerSort
decl_stmt|;
DECL|field|savePriDesc
specifier|private
name|JCheckBox
name|savePriDesc
decl_stmt|;
DECL|field|saveSecDesc
specifier|private
name|JCheckBox
name|saveSecDesc
decl_stmt|;
DECL|field|saveTerDesc
specifier|private
name|JCheckBox
name|saveTerDesc
decl_stmt|;
DECL|method|SaveOrderConfigDisplay ()
specifier|public
name|SaveOrderConfigDisplay
parameter_list|()
block|{
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
name|InternalBibtexFields
operator|.
name|getAllPublicFieldNames
argument_list|()
decl_stmt|;
name|fieldNames
operator|.
name|add
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|String
index|[]
name|allPlusKey
init|=
name|fieldNames
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|fieldNames
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|savePriSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|saveSecSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|saveTerSort
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|allPlusKey
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|savePriDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|saveSecDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|saveTerDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"right:pref, 8dlu, fill:pref, 4dlu, fill:60dlu, 4dlu, left:pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref"
argument_list|)
decl_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Primary sort criterion"
argument_list|)
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
name|savePriSort
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|savePriDesc
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Secondary sort criterion"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveSecSort
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveSecDesc
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tertiary sort criterion"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveTerSort
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|saveTerDesc
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|panel
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
DECL|method|getPanel ()
specifier|public
name|Component
name|getPanel
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
DECL|method|setEnabled (boolean enabled)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|savePriSort
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|savePriDesc
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveSecDesc
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|saveTerDesc
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
block|}
DECL|method|setSaveOrderConfig (SaveOrderConfig saveOrderConfig)
specifier|public
name|void
name|setSaveOrderConfig
parameter_list|(
name|SaveOrderConfig
name|saveOrderConfig
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|saveOrderConfig
argument_list|)
expr_stmt|;
name|savePriSort
operator|.
name|setSelectedItem
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|savePriDesc
operator|.
name|setSelected
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|saveSecSort
operator|.
name|setSelectedItem
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|saveSecDesc
operator|.
name|setSelected
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|saveTerSort
operator|.
name|setSelectedItem
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|saveTerDesc
operator|.
name|setSelected
argument_list|(
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
block|}
DECL|method|getSaveOrderConfig ()
specifier|public
name|SaveOrderConfig
name|getSaveOrderConfig
parameter_list|()
block|{
name|SaveOrderConfig
name|saveOrderConfig
init|=
operator|new
name|SaveOrderConfig
argument_list|()
decl_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
operator|=
name|getSelectedItemAsLowerCaseTrim
argument_list|(
name|savePriSort
argument_list|)
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
operator|=
name|savePriDesc
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
operator|=
name|getSelectedItemAsLowerCaseTrim
argument_list|(
name|saveSecSort
argument_list|)
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
operator|=
name|saveSecDesc
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
operator|=
name|getSelectedItemAsLowerCaseTrim
argument_list|(
name|saveTerSort
argument_list|)
expr_stmt|;
name|saveOrderConfig
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
operator|=
name|saveTerDesc
operator|.
name|isSelected
argument_list|()
expr_stmt|;
return|return
name|saveOrderConfig
return|;
block|}
DECL|method|getSelectedItemAsLowerCaseTrim (JComboBox<String> sortBox)
specifier|private
name|String
name|getSelectedItemAsLowerCaseTrim
parameter_list|(
name|JComboBox
argument_list|<
name|String
argument_list|>
name|sortBox
parameter_list|)
block|{
return|return
name|sortBox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
return|;
block|}
block|}
end_class

end_unit

