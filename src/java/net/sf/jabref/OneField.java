begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|util
operator|.
name|HashMap
import|;
end_import

begin_class
DECL|class|OneField
specifier|public
class|class
name|OneField
block|{
DECL|field|_name
specifier|private
name|String
name|_name
decl_stmt|;
DECL|field|_value
specifier|private
name|String
name|_value
decl_stmt|;
DECL|field|_stringType
specifier|private
name|int
name|_stringType
decl_stmt|;
comment|// enum
comment|// STRING_NONE : does not have a @string keyword
comment|// STRING_SIMPLE,: has only One keyword
comment|// STRING_COMPOSITE: has multiple (string concatenation)
DECL|field|_simpleString
name|String
name|_simpleString
decl_stmt|;
DECL|field|_compositeLeft
name|String
name|_compositeLeft
decl_stmt|;
DECL|field|_compositeRight
name|String
name|_compositeRight
decl_stmt|;
block|}
end_class

end_unit

