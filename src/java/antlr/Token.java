begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_comment
comment|/** A token is minimally a token type.  Subclasses can add the text matched  *  for the token and line info.  */
end_comment

begin_class
DECL|class|Token
specifier|public
class|class
name|Token
implements|implements
name|Cloneable
block|{
comment|// constants
DECL|field|MIN_USER_TYPE
specifier|public
specifier|static
specifier|final
name|int
name|MIN_USER_TYPE
init|=
literal|4
decl_stmt|;
DECL|field|NULL_TREE_LOOKAHEAD
specifier|public
specifier|static
specifier|final
name|int
name|NULL_TREE_LOOKAHEAD
init|=
literal|3
decl_stmt|;
DECL|field|INVALID_TYPE
specifier|public
specifier|static
specifier|final
name|int
name|INVALID_TYPE
init|=
literal|0
decl_stmt|;
DECL|field|EOF_TYPE
specifier|public
specifier|static
specifier|final
name|int
name|EOF_TYPE
init|=
literal|1
decl_stmt|;
DECL|field|SKIP
specifier|public
specifier|static
specifier|final
name|int
name|SKIP
init|=
operator|-
literal|1
decl_stmt|;
comment|// each Token has at least a token type
DECL|field|type
name|int
name|type
init|=
name|INVALID_TYPE
decl_stmt|;
comment|// the illegal token object
DECL|field|badToken
specifier|public
specifier|static
name|Token
name|badToken
init|=
operator|new
name|Token
argument_list|(
name|INVALID_TYPE
argument_list|,
literal|"<no text>"
argument_list|)
decl_stmt|;
DECL|method|Token ()
specifier|public
name|Token
parameter_list|()
block|{     }
DECL|method|Token (int t)
specifier|public
name|Token
parameter_list|(
name|int
name|t
parameter_list|)
block|{
name|type
operator|=
name|t
expr_stmt|;
block|}
DECL|method|Token (int t, String txt)
specifier|public
name|Token
parameter_list|(
name|int
name|t
parameter_list|,
name|String
name|txt
parameter_list|)
block|{
name|type
operator|=
name|t
expr_stmt|;
name|setText
argument_list|(
name|txt
argument_list|)
expr_stmt|;
block|}
DECL|method|getColumn ()
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
DECL|method|getFilename ()
specifier|public
name|String
name|getFilename
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
DECL|method|setFilename (String name)
specifier|public
name|void
name|setFilename
parameter_list|(
name|String
name|name
parameter_list|)
block|{ 	}
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
literal|"<no text>"
return|;
block|}
DECL|method|setText (String t)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|t
parameter_list|)
block|{     }
DECL|method|setColumn (int c)
specifier|public
name|void
name|setColumn
parameter_list|(
name|int
name|c
parameter_list|)
block|{     }
DECL|method|setLine (int l)
specifier|public
name|void
name|setLine
parameter_list|(
name|int
name|l
parameter_list|)
block|{     }
DECL|method|getType ()
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
DECL|method|setType (int t)
specifier|public
name|void
name|setType
parameter_list|(
name|int
name|t
parameter_list|)
block|{
name|type
operator|=
name|t
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"[\""
operator|+
name|getText
argument_list|()
operator|+
literal|"\",<"
operator|+
name|type
operator|+
literal|">]"
return|;
block|}
block|}
end_class

end_unit

