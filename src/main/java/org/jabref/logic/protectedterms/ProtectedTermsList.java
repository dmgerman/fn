begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.protectedterms
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|protectedterms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|StandardOpenOption
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|ProtectedTermsList
specifier|public
class|class
name|ProtectedTermsList
implements|implements
name|Comparable
argument_list|<
name|ProtectedTermsList
argument_list|>
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ProtectedTermsList
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|description
specifier|private
name|String
name|description
decl_stmt|;
DECL|field|termsList
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|termsList
decl_stmt|;
DECL|field|location
specifier|private
specifier|final
name|String
name|location
decl_stmt|;
DECL|field|internalList
specifier|private
specifier|final
name|boolean
name|internalList
decl_stmt|;
DECL|field|enabled
specifier|private
name|boolean
name|enabled
decl_stmt|;
DECL|method|ProtectedTermsList (String description, List<String> termList, String location, boolean internalList)
specifier|public
name|ProtectedTermsList
parameter_list|(
name|String
name|description
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|termList
parameter_list|,
name|String
name|location
parameter_list|,
name|boolean
name|internalList
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|this
operator|.
name|termsList
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|termList
argument_list|)
expr_stmt|;
name|this
operator|.
name|location
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|this
operator|.
name|internalList
operator|=
name|internalList
expr_stmt|;
block|}
DECL|method|ProtectedTermsList (String description, List<String> termList, String location)
specifier|public
name|ProtectedTermsList
parameter_list|(
name|String
name|description
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|termList
parameter_list|,
name|String
name|location
parameter_list|)
block|{
name|this
argument_list|(
name|description
argument_list|,
name|termList
argument_list|,
name|location
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
DECL|method|getTermList ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getTermList
parameter_list|()
block|{
return|return
name|termsList
return|;
block|}
DECL|method|getLocation ()
specifier|public
name|String
name|getLocation
parameter_list|()
block|{
return|return
name|location
return|;
block|}
DECL|method|getTermListing ()
specifier|public
name|String
name|getTermListing
parameter_list|()
block|{
return|return
name|String
operator|.
name|join
argument_list|(
literal|"\n"
argument_list|,
name|termsList
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (ProtectedTermsList otherList)
specifier|public
name|int
name|compareTo
parameter_list|(
name|ProtectedTermsList
name|otherList
parameter_list|)
block|{
return|return
name|this
operator|.
name|getDescription
argument_list|()
operator|.
name|compareTo
argument_list|(
name|otherList
operator|.
name|getDescription
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isInternalList ()
specifier|public
name|boolean
name|isInternalList
parameter_list|()
block|{
return|return
name|internalList
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
name|this
operator|.
name|enabled
operator|=
name|enabled
expr_stmt|;
block|}
DECL|method|isEnabled ()
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|enabled
return|;
block|}
DECL|method|createAndWriteHeading (String newDescription)
specifier|public
name|boolean
name|createAndWriteHeading
parameter_list|(
name|String
name|newDescription
parameter_list|)
block|{
name|description
operator|=
name|newDescription
expr_stmt|;
return|return
name|addProtectedTerm
argument_list|(
literal|"# "
operator|+
name|newDescription
argument_list|,
literal|true
argument_list|)
return|;
block|}
DECL|method|addProtectedTerm (String term)
specifier|public
name|boolean
name|addProtectedTerm
parameter_list|(
name|String
name|term
parameter_list|)
block|{
return|return
name|addProtectedTerm
argument_list|(
name|term
argument_list|,
literal|false
argument_list|)
return|;
block|}
DECL|method|addProtectedTerm (String term, boolean create)
specifier|public
name|boolean
name|addProtectedTerm
parameter_list|(
name|String
name|term
parameter_list|,
name|boolean
name|create
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|term
argument_list|)
expr_stmt|;
comment|// Cannot add to internal lists
if|if
condition|(
name|internalList
condition|)
block|{
return|return
literal|false
return|;
block|}
name|Path
name|p
init|=
name|Paths
operator|.
name|get
argument_list|(
name|location
argument_list|)
decl_stmt|;
name|String
name|s
init|=
name|OS
operator|.
name|NEWLINE
operator|+
name|term
decl_stmt|;
try|try
init|(
name|BufferedWriter
name|writer
init|=
name|Files
operator|.
name|newBufferedWriter
argument_list|(
name|p
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|,
name|create
condition|?
name|StandardOpenOption
operator|.
name|CREATE
else|:
name|StandardOpenOption
operator|.
name|APPEND
argument_list|)
init|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|termsList
operator|.
name|add
argument_list|(
name|term
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioe
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem adding protected term to list"
argument_list|,
name|ioe
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
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
operator|!
operator|(
name|o
operator|instanceof
name|ProtectedTermsList
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|ProtectedTermsList
name|otherList
init|=
operator|(
name|ProtectedTermsList
operator|)
name|o
decl_stmt|;
return|return
operator|(
name|this
operator|.
name|location
operator|.
name|equals
argument_list|(
name|otherList
operator|.
name|location
argument_list|)
operator|)
operator|&&
operator|(
name|this
operator|.
name|description
operator|.
name|equals
argument_list|(
name|otherList
operator|.
name|description
argument_list|)
operator|)
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
name|location
argument_list|,
name|description
argument_list|)
return|;
block|}
block|}
end_class

end_unit

