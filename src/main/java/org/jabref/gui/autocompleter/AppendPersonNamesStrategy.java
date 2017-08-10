begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.autocompleter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
package|;
end_package

begin_class
DECL|class|AppendPersonNamesStrategy
specifier|public
class|class
name|AppendPersonNamesStrategy
extends|extends
name|AppendWordsStrategy
block|{
comment|/**      * true if the input should be split at a single white space instead of the usual delimiter " and " for names.      * Useful if the input consists of a list of last names.      */
DECL|field|separationBySpace
specifier|private
specifier|final
name|boolean
name|separationBySpace
decl_stmt|;
DECL|method|AppendPersonNamesStrategy ()
specifier|public
name|AppendPersonNamesStrategy
parameter_list|()
block|{
name|this
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|AppendPersonNamesStrategy (boolean separationBySpace)
specifier|public
name|AppendPersonNamesStrategy
parameter_list|(
name|boolean
name|separationBySpace
parameter_list|)
block|{
name|this
operator|.
name|separationBySpace
operator|=
name|separationBySpace
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getDelimiter ()
specifier|public
name|String
name|getDelimiter
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|separationBySpace
condition|)
block|{
return|return
literal|" "
return|;
block|}
else|else
block|{
return|return
literal|" and "
return|;
block|}
block|}
block|}
end_class

end_unit

