/* eslint-disable react/prefer-stateless-function */
import React from 'react';
import {Layout} from 'antd';

class Footer extends React.PureComponent {
    render() {
        return (
            <Layout.Footer style={{textAlign: 'center'}}>
        Copyright © {new Date().getFullYear() - 1}-{new Date().getFullYear()} All rights reserved.
            </Layout.Footer>
        );
    }
}

Footer.propTypes = {

};

export default Footer;
