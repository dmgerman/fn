begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
package|;
end_package

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
name|Map
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
name|TableColumn
operator|.
name|SortType
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
name|field
operator|.
name|SpecialField
import|;
end_import

begin_class
DECL|class|ColumnPreferences
specifier|public
class|class
name|ColumnPreferences
block|{
DECL|field|DEFAULT_FIELD_LENGTH
specifier|public
specifier|static
specifier|final
name|double
name|DEFAULT_FIELD_LENGTH
init|=
literal|100
decl_stmt|;
DECL|field|showFileColumn
specifier|private
specifier|final
name|boolean
name|showFileColumn
decl_stmt|;
DECL|field|showUrlColumn
specifier|private
specifier|final
name|boolean
name|showUrlColumn
decl_stmt|;
DECL|field|preferDoiOverUrl
specifier|private
specifier|final
name|boolean
name|preferDoiOverUrl
decl_stmt|;
DECL|field|showEprintColumn
specifier|private
specifier|final
name|boolean
name|showEprintColumn
decl_stmt|;
DECL|field|normalColumns
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|normalColumns
decl_stmt|;
DECL|field|specialFieldColumns
specifier|private
specifier|final
name|List
argument_list|<
name|SpecialField
argument_list|>
name|specialFieldColumns
decl_stmt|;
DECL|field|extraFileColumns
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|extraFileColumns
decl_stmt|;
DECL|field|columnWidths
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|columnWidths
decl_stmt|;
DECL|field|columnSortType
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|SortType
argument_list|>
name|columnSortType
decl_stmt|;
DECL|method|ColumnPreferences (boolean showFileColumn, boolean showUrlColumn, boolean preferDoiOverUrl, boolean showEprintColumn, List<String> normalColumns, List<SpecialField> specialFieldColumns, List<String> extraFileColumns, Map<String, Double> columnWidths, Map<String, SortType> columnSortType)
specifier|public
name|ColumnPreferences
parameter_list|(
name|boolean
name|showFileColumn
parameter_list|,
name|boolean
name|showUrlColumn
parameter_list|,
name|boolean
name|preferDoiOverUrl
parameter_list|,
name|boolean
name|showEprintColumn
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|normalColumns
parameter_list|,
name|List
argument_list|<
name|SpecialField
argument_list|>
name|specialFieldColumns
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extraFileColumns
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|columnWidths
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|SortType
argument_list|>
name|columnSortType
parameter_list|)
block|{
name|this
operator|.
name|showFileColumn
operator|=
name|showFileColumn
expr_stmt|;
name|this
operator|.
name|showUrlColumn
operator|=
name|showUrlColumn
expr_stmt|;
name|this
operator|.
name|preferDoiOverUrl
operator|=
name|preferDoiOverUrl
expr_stmt|;
name|this
operator|.
name|showEprintColumn
operator|=
name|showEprintColumn
expr_stmt|;
name|this
operator|.
name|normalColumns
operator|=
name|normalColumns
expr_stmt|;
name|this
operator|.
name|specialFieldColumns
operator|=
name|specialFieldColumns
expr_stmt|;
name|this
operator|.
name|extraFileColumns
operator|=
name|extraFileColumns
expr_stmt|;
name|this
operator|.
name|columnWidths
operator|=
name|columnWidths
expr_stmt|;
name|this
operator|.
name|columnSortType
operator|=
name|columnSortType
expr_stmt|;
block|}
DECL|method|showFileColumn ()
specifier|public
name|boolean
name|showFileColumn
parameter_list|()
block|{
return|return
name|showFileColumn
return|;
block|}
DECL|method|showUrlColumn ()
specifier|public
name|boolean
name|showUrlColumn
parameter_list|()
block|{
return|return
name|showUrlColumn
return|;
block|}
DECL|method|preferDoiOverUrl ()
specifier|public
name|boolean
name|preferDoiOverUrl
parameter_list|()
block|{
return|return
name|preferDoiOverUrl
return|;
block|}
DECL|method|showEprintColumn ()
specifier|public
name|boolean
name|showEprintColumn
parameter_list|()
block|{
return|return
name|showEprintColumn
return|;
block|}
DECL|method|getExtraFileColumns ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExtraFileColumns
parameter_list|()
block|{
return|return
name|extraFileColumns
return|;
block|}
DECL|method|getSpecialFieldColumns ()
specifier|public
name|List
argument_list|<
name|SpecialField
argument_list|>
name|getSpecialFieldColumns
parameter_list|()
block|{
return|return
name|specialFieldColumns
return|;
block|}
DECL|method|getNormalColumns ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getNormalColumns
parameter_list|()
block|{
return|return
name|normalColumns
return|;
block|}
DECL|method|getPrefColumnWidth (String columnName)
specifier|public
name|double
name|getPrefColumnWidth
parameter_list|(
name|String
name|columnName
parameter_list|)
block|{
return|return
name|columnWidths
operator|.
name|getOrDefault
argument_list|(
name|columnName
argument_list|,
name|DEFAULT_FIELD_LENGTH
argument_list|)
return|;
block|}
DECL|method|getSortTypesForColumns ()
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|SortType
argument_list|>
name|getSortTypesForColumns
parameter_list|()
block|{
return|return
name|columnSortType
return|;
block|}
block|}
end_class

end_unit

