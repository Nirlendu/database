/* Generated By:JJTree: Do not edit this line. ASTLoad.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.bigdata.rdf.sail.sparql.ast;

import org.openrdf.rio.RDFParser.DatatypeHandling;

public
class ASTLoad extends ASTUpdate {

    private boolean silent;
    
    /*
     * Note: These values default to [null] so we can inherit the default
     * behavior as configured for the KB when an option is not explicitly
     * specified.
     */
    
    public DatatypeHandling datatypeHandling = null;

    public Boolean preserveBNodeIDs = null;

    public Boolean stopAtFirstError = null;

    public Boolean verifyData = null;

    public ASTLoad(int id) {
        super(id);
    }

    public ASTLoad(SyntaxTreeBuilder p, int id) {
        super(p, id);
    }

    /** Accept the visitor. **/
    public Object jjtAccept(SyntaxTreeBuilderVisitor visitor, Object data)
            throws VisitorException {
        return visitor.visit(this, data);
    }

    public void setSilent(final boolean silent) {
        this.silent = silent;
    }

    public boolean isSilent() {
        return this.silent;
    }

}
/*
 * JavaCC - OriginalChecksum=b83ece3152041c4178153a0f76debe55 (do not edit this
 * line)
 */
