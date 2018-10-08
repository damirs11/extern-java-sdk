package ru.kontur.extern_api.sdk.model;

import org.jetbrains.annotations.Nullable;

public class OrgFilter {

    private long skip;
    private int take = 1000;

    private String inn;

    @Nullable
    private String kpp;

    private OrgFilter skip(long skip) {
        this.skip = skip;
        return this;
    }

    private OrgFilter take(int take) {
        this.take = take;
        return this;
    }

    public OrgFilter inn(String inn) {
        this.inn = inn;
        return this;
    }

    public OrgFilter kpp(String kpp) {
        this.kpp = kpp;
        return this;
    }

    public static OrgFilter page(long skip, int take) {
        return new OrgFilter()
                .skip(skip)
                .take(take);
    }

    public static OrgFilter page(int skip, int take) {
        return page((long) skip, take);
    }

    public long getSkip() {
        return skip;
    }

    public int getTake() {
        return take;
    }

    public String getInn() {
        return inn;
    }

    @Nullable
    public String getKpp() {
        return kpp;
    }
}
