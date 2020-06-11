<template>
    <div>

        <div class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/#">
                        <img src="../../assets/images/logo.gif" alt="EA2 Logo"/>
                    </a>
                </div>
                <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                    <ul class="nav navbar-nav navbar-right">
                        <dropdown tag="li">
                            <router-link to="/">router1:Go to Welcome</router-link>
                        </dropdown>
                        <dropdown tag="li">
                            <router-link to="/demo">router2:Go to Demo</router-link>
                        </dropdown>
                        <dropdown tag="li">
                            <router-link to="/table">router2:Go to Table</router-link>
                        </dropdown>
                    </ul>
                </div>
            </div>
        </div>

        <div class="svg" role="presentation">
            <div class="logo-container">
                <img src="../../assets/images/ea.jpg" class="logo"/>
                <span class="plus-logo">+</span>
                <img src="../../assets/logo.png" class="hero-log"/>
            </div>
        </div>

        <div style="text-align:center">
            <slot></slot>
            <div class="footer" role="contentinfo"></div>
        </div>
    </div>
</template>

<script>
    export default {}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    .navbar-nav li button {
        background: transparent;
        border: none;
        line-height: 20px;
        font-size: 18px;
        color: white;
        padding: 25px 15px;
    }

    .btn-primary:active, .btn-primary.active, .open > .dropdown-toggle.btn-primary {
        background-color: #e7e7e7;
    }

    button span.caret {
        margin-left: 2px;
    }

    .plus-logo {
        font-size: 10rem;
        /*margin-left: -100px;*/
        margin-right: -13px;
    }

    .hero-logo {
        width: 161px;
        margin-right: -161px;
        margin-bottom: 88px;
    }
</style>
