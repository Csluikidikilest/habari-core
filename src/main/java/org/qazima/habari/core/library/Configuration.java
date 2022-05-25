package org.qazima.habari.core.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private Map<String, String> _availablePlugins = new HashMap<>();
    private int _defaultPageSize = 50;
    private boolean _isDeleteAllowed = false;
    private boolean _isGetAllowed = false;
    private boolean _isPostAllowed = false;
    private boolean _isPutAllowed = false;
    //private List<IPlugin> Plugins = new ArrayList<>();
    private List<Server> _servers = new ArrayList<>();

    public Map<String, String> availablePlugins() { return _availablePlugins; }
    public int defaultPageSize() { return _defaultPageSize; }
    public boolean isDeleteAllowed() { return _isDeleteAllowed; };
    public boolean isGetAllowed() { return _isGetAllowed; };
    public boolean isPostAllowed() { return _isPostAllowed; };
    public boolean isPutAllowed() { return _isPutAllowed; };
    public List<Server> servers() { return _servers; }

    protected void defaultPageSize(int value) { _defaultPageSize = value; };
    protected void isDeleteAllowed(boolean value) { _isDeleteAllowed = value; };
    protected void isGetAllowed(boolean value) { _isGetAllowed = value; };
    protected void isPostAllowed(boolean value) { _isPostAllowed = value; };
    protected void isPutAllowed(boolean value) { _isPutAllowed = value; };
    protected void isPutAllowed(List<Server> value) { _servers = value; };
}
