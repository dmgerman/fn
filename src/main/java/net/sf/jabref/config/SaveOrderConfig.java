begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.config
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|config
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_comment
comment|/**  * Stores the save order config from MetaData  *   * Format:<choice>, pair of field + ascending (boolean)  */
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
DECL|field|saveInSpecifiedOrder
specifier|public
name|boolean
name|saveInSpecifiedOrder
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
init|=
literal|false
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
DECL|method|SaveOrderConfig (Vector<String> data)
specifier|public
name|SaveOrderConfig
parameter_list|(
name|Vector
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
name|elementAt
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
name|elementAt
argument_list|(
literal|1
argument_list|)
argument_list|,
name|data
operator|.
name|elementAt
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
name|elementAt
argument_list|(
literal|3
argument_list|)
argument_list|,
name|data
operator|.
name|elementAt
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
name|elementAt
argument_list|(
literal|5
argument_list|)
argument_list|,
name|data
operator|.
name|elementAt
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
name|this
operator|.
name|saveInSpecifiedOrder
operator|=
literal|false
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
name|this
operator|.
name|saveInSpecifiedOrder
operator|=
literal|true
expr_stmt|;
block|}
comment|/**      * Outputs the current configuration to be consumed later by the constructor      */
DECL|method|getVector ()
specifier|public
name|Vector
argument_list|<
name|String
argument_list|>
name|getVector
parameter_list|()
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
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
name|insertElementAt
argument_list|(
literal|"original"
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
assert|assert
name|saveInSpecifiedOrder
assert|;
name|res
operator|.
name|insertElementAt
argument_list|(
literal|"specified"
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|res
operator|.
name|insertElementAt
argument_list|(
name|sortCriteria
index|[
literal|0
index|]
operator|.
name|field
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|res
operator|.
name|insertElementAt
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
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|res
operator|.
name|insertElementAt
argument_list|(
name|sortCriteria
index|[
literal|1
index|]
operator|.
name|field
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|res
operator|.
name|insertElementAt
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
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|res
operator|.
name|insertElementAt
argument_list|(
name|sortCriteria
index|[
literal|2
index|]
operator|.
name|field
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|res
operator|.
name|insertElementAt
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
argument_list|,
literal|6
argument_list|)
expr_stmt|;
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

