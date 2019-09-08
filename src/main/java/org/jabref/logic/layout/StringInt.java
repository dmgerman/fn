begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
package|;
end_package

begin_comment
comment|/**  * String and integer value.  */
end_comment

begin_class
DECL|class|StringInt
specifier|public
class|class
name|StringInt
implements|implements
name|java
operator|.
name|io
operator|.
name|Serializable
block|{
comment|/**      *  Description of the Field      */
DECL|field|s
specifier|public
name|String
name|s
decl_stmt|;
comment|/**      *  Description of the Field      */
DECL|field|i
specifier|public
specifier|final
name|int
name|i
decl_stmt|;
comment|/**      *  Constructor for the StringInt object      *      */
DECL|method|StringInt (final String s, final int i)
specifier|public
name|StringInt
parameter_list|(
specifier|final
name|String
name|s
parameter_list|,
specifier|final
name|int
name|i
parameter_list|)
block|{
name|this
operator|.
name|s
operator|=
name|s
expr_stmt|;
name|this
operator|.
name|i
operator|=
name|i
expr_stmt|;
block|}
block|}
end_class

end_unit

