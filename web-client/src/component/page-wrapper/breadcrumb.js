import React from 'react';
import PropTypes from 'prop-types';
import {Breadcrumb} from 'antd';
import {Link} from 'react-router-dom';

class StrengtheningBreadcrumb extends React.PureComponent {
    getBreadcrumbItem(datas) {
        return datas.map(element => (
            <Breadcrumb.Item key={element.key}>
                {
                    element.link
                        ? <Link to={element.link}>{element.text}</Link>
                        : element.text
                }
            </Breadcrumb.Item>
        ));
    }

  getRouteData = () => {
      const newRouteData = [
          {
              key: '/',
              link: '/',
              text: '首页'
          },
          ...this.props.routeData,
      ];

      return newRouteData;
  }

  render() {
      const routeData = this.getRouteData();
      return (
          <React.Fragment>
              {
                  routeData.length > 1 &&
          <React.Fragment>
              <Breadcrumb>
                  {this.getBreadcrumbItem(routeData)}
              </Breadcrumb>
          </React.Fragment>
              }
          </React.Fragment>
      );
  }
}

StrengtheningBreadcrumb.propTypes = {
    routeData: PropTypes.array.isRequired
};

StrengtheningBreadcrumb.defaultProps = {
    routeData: []
};

export default StrengtheningBreadcrumb;
