begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.config
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|config
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
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Stores the save order config from MetaData  *<p>  * Format:<choice>, pair of field + ascending (boolean)  */
end_comment

begin_class
DECL|class|SaveOrderConfig
specifier|public
class|class
name|SaveOrderConfig
block|{
DECL|field|saveInOriginalOrder
specifier|public
name|boolean
name|saveInOriginalOrder
decl_stmt|;
comment|// quick hack for outside modifications
DECL|field|sortCriteria
specifier|public
specifier|final
name|SortCriterion
index|[]
name|sortCriteria
init|=
operator|new
name|SortCriterion
index|[
literal|3
index|]
decl_stmt|;
DECL|class|SortCriterion
specifier|public
specifier|static
class|class
name|SortCriterion
block|{
DECL|field|field
specifier|public
name|String
name|field
decl_stmt|;
DECL|field|descending
specifier|public
name|boolean
name|descending
decl_stmt|;
DECL|method|SortCriterion ()
specifier|public
name|SortCriterion
parameter_list|()
block|{
name|this
operator|.
name|field
operator|=
literal|""
expr_stmt|;
block|}
DECL|method|SortCriterion (String field, String descending)
specifier|public
name|SortCriterion
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|descending
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|descending
operator|=
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|descending
argument_list|)
expr_stmt|;
block|}
DECL|method|SortCriterion (String field, boolean descending)
specifier|public
name|SortCriterion
parameter_list|(
name|String
name|field
parameter_list|,
name|boolean
name|descending
parameter_list|)
block|{
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|descending
operator|=
name|descending
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"SortCriterion{"
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"field='"
argument_list|)
operator|.
name|append
argument_list|(
name|field
argument_list|)
operator|.
name|append
argument_list|(
literal|'\''
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", descending="
argument_list|)
operator|.
name|append
argument_list|(
name|descending
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|SortCriterion
name|that
init|=
operator|(
name|SortCriterion
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|descending
argument_list|,
name|that
operator|.
name|descending
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|field
argument_list|,
name|that
operator|.
name|field
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|field
argument_list|,
name|descending
argument_list|)
return|;
block|}
block|}
DECL|method|SaveOrderConfig (boolean saveInOriginalOrder, SortCriterion first, SortCriterion second, SortCriterion third)
specifier|public
name|SaveOrderConfig
parameter_list|(
name|boolean
name|saveInOriginalOrder
parameter_list|,
name|SortCriterion
name|first
parameter_list|,
name|SortCriterion
name|second
parameter_list|,
name|SortCriterion
name|third
parameter_list|)
block|{
name|this
operator|.
name|saveInOriginalOrder
operator|=
name|saveInOriginalOrder
expr_stmt|;
name|sortCriteria
index|[
literal|0
index|]
operator|=
name|first
expr_stmt|;
name|sortCriteria
index|[
literal|1
index|]
operator|=
name|second
expr_stmt|;
name|sortCriteria
index|[
literal|2
index|]
operator|=
name|third
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|o
operator|instanceof
name|SaveOrderConfig
condition|)
block|{
name|SaveOrderConfig
name|that
init|=
operator|(
name|SaveOrderConfig
operator|)
name|o
decl_stmt|;
name|boolean
name|sortCriteriaEquals
init|=
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|that
operator|.
name|sortCriteria
index|[
literal|0
index|]
argument_list|)
operator|&&
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
name|that
operator|.
name|sortCriteria
index|[
literal|1
index|]
argument_list|)
operator|&&
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|equals
argument_list|(
name|that
operator|.
name|sortCriteria
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|saveInOriginalOrder
argument_list|,
name|that
operator|.
name|saveInOriginalOrder
argument_list|)
operator|&&
name|sortCriteriaEquals
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|saveInOriginalOrder
argument_list|,
name|Arrays
operator|.
name|hashCode
argument_list|(
name|sortCriteria
argument_list|)
argument_list|)
return|;
block|}
DECL|method|SaveOrderConfig ()
specifier|public
name|SaveOrderConfig
parameter_list|()
block|{
comment|// fill default values
name|setSaveInOriginalOrder
argument_list|()
expr_stmt|;
name|sortCriteria
index|[
literal|0
index|]
operator|=
operator|new
name|SortCriterion
argument_list|()
expr_stmt|;
name|sortCriteria
index|[
literal|1
index|]
operator|=
operator|new
name|SortCriterion
argument_list|()
expr_stmt|;
name|sortCriteria
index|[
literal|2
index|]
operator|=
operator|new
name|SortCriterion
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"SaveOrderConfig{"
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"saveInOriginalOrder="
argument_list|)
operator|.
name|append
argument_list|(
name|saveInOriginalOrder
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|", sortCriteria="
argument_list|)
operator|.
name|append
argument_list|(
name|Arrays
operator|.
name|toString
argument_list|(
name|sortCriteria
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'}'
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|SaveOrderConfig (List<String> data)
specifier|public
name|SaveOrderConfig
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|data
parameter_list|)
block|{
if|if
condition|(
name|data
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NullPointerException
argument_list|()
throw|;
block|}
if|if
condition|(
name|data
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|()
throw|;
block|}
name|String
name|choice
init|=
name|data
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"original"
operator|.
name|equals
argument_list|(
name|choice
argument_list|)
condition|)
block|{
name|setSaveInOriginalOrder
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|setSaveInSpecifiedOrder
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|data
operator|.
name|size
argument_list|()
operator|>=
literal|3
condition|)
block|{
name|sortCriteria
index|[
literal|0
index|]
operator|=
operator|new
name|SortCriterion
argument_list|(
name|data
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|data
operator|.
name|get
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sortCriteria
index|[
literal|0
index|]
operator|=
operator|new
name|SortCriterion
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|data
operator|.
name|size
argument_list|()
operator|>=
literal|5
condition|)
block|{
name|sortCriteria
index|[
literal|1
index|]
operator|=
operator|new
name|SortCriterion
argument_list|(
name|data
operator|.
name|get
argument_list|(
literal|3
argument_list|)
argument_list|,
name|data
operator|.
name|get
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sortCriteria
index|[
literal|1
index|]
operator|=
operator|new
name|SortCriterion
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|data
operator|.
name|size
argument_list|()
operator|>=
literal|7
condition|)
block|{
name|sortCriteria
index|[
literal|2
index|]
operator|=
operator|new
name|SortCriterion
argument_list|(
name|data
operator|.
name|get
argument_list|(
literal|5
argument_list|)
argument_list|,
name|data
operator|.
name|get
argument_list|(
literal|6
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sortCriteria
index|[
literal|2
index|]
operator|=
operator|new
name|SortCriterion
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|setSaveInOriginalOrder ()
specifier|public
name|void
name|setSaveInOriginalOrder
parameter_list|()
block|{
name|this
operator|.
name|saveInOriginalOrder
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|setSaveInSpecifiedOrder ()
specifier|public
name|void
name|setSaveInSpecifiedOrder
parameter_list|()
block|{
name|this
operator|.
name|saveInOriginalOrder
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|loadExportSaveOrderFromPreferences (JabRefPreferences preferences)
specifier|public
specifier|static
name|SaveOrderConfig
name|loadExportSaveOrderFromPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|SaveOrderConfig
name|config
init|=
operator|new
name|SaveOrderConfig
argument_list|()
decl_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_FIELD
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_DESCENDING
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_FIELD
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_DESCENDING
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_FIELD
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_DESCENDING
argument_list|)
expr_stmt|;
return|return
name|config
return|;
block|}
DECL|method|loadTableSaveOrderFromPreferences (JabRefPreferences preferences)
specifier|public
specifier|static
name|SaveOrderConfig
name|loadTableSaveOrderFromPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|SaveOrderConfig
name|config
init|=
operator|new
name|SaveOrderConfig
argument_list|()
decl_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_PRIMARY_SORT_FIELD
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_PRIMARY_SORT_DESCENDING
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_SECONDARY_SORT_FIELD
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_SECONDARY_SORT_DESCENDING
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TERTIARY_SORT_FIELD
argument_list|)
expr_stmt|;
name|config
operator|.
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TERTIARY_SORT_DESCENDING
argument_list|)
expr_stmt|;
return|return
name|config
return|;
block|}
DECL|method|storeAsExportSaveOrderInPreferences (JabRefPreferences preferences)
specifier|public
name|void
name|storeAsExportSaveOrderInPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_DESCENDING
argument_list|,
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_DESCENDING
argument_list|,
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_DESCENDING
argument_list|,
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_PRIMARY_SORT_FIELD
argument_list|,
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_SECONDARY_SORT_FIELD
argument_list|,
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_TERTIARY_SORT_FIELD
argument_list|,
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
block|}
comment|/**      * Outputs the current configuration to be consumed later by the constructor      */
DECL|method|getConfigurationList ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getConfigurationList
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|7
argument_list|)
decl_stmt|;
if|if
condition|(
name|saveInOriginalOrder
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
literal|"original"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|res
operator|.
name|add
argument_list|(
literal|"specified"
argument_list|)
expr_stmt|;
block|}
name|res
operator|.
name|add
argument_list|(
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|Boolean
operator|.
name|toString
argument_list|(
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|descending
argument_list|)
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|Boolean
operator|.
name|toString
argument_list|(
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|descending
argument_list|)
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|Boolean
operator|.
name|toString
argument_list|(
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|descending
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

